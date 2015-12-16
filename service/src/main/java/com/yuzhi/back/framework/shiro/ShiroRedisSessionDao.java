package com.yuzhi.back.framework.shiro;

import com.yuzhi.back.framework.RedisManager;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by tiansj on 15/8/10.
 */
public class ShiroRedisSessionDao extends CachingSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(ShiroRedisSessionDao.class);
    private String keyPrefix = "shiro_redis_session:";
    private int sessionTimeout = 1800000;

    @Resource
    private RedisManager redisManager;


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("=> read session with ID [{}]", sessionId);
        if(sessionId == null){
            logger.error("session id is null");
            return null;
        }

        // 例如 Redis 调用 flushdb 情况了所有的数据，读到的 session 就是空的
        byte[] value = redisManager.get(this.getByteKey(sessionId));
        if(value != null) {
            Session s = (Session) SerializationUtils.deserialize(value);
            // super.cache(s, s.getId());
            return s;
        }

        return null;
    }

    @Override
    protected void doUpdate(Session session) {
        // 如果会话过期/停止，没必要再更新了
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            logger.debug("=> Invalid session.");
            return;
        }

        logger.debug("=> update session with ID [{}]", session.getId());
        this.saveSession(session);
    }

    /**
     * save session
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException{
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }

        byte[] key = getByteKey(session.getId());
        byte[] value = SerializationUtils.serialize((Serializable) session);
        session.setTimeout(sessionTimeout);
        this.redisManager.set(key, value, sessionTimeout);
    }

    @Override
    protected void doDelete(Session session) {
        logger.debug("=> delete session with ID [{}]", session.getId());
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }
        redisManager.del(this.getByteKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        logger.debug("=> get active sessions");

        /*Set<Session> sessions = new HashSet<Session>();
        Set<byte[]> keys = redisManager.keys(this.keyPrefix + "*");
        if(keys != null && keys.size()>0){
            for(byte[] key:keys){
                Session s = SerializationUtils.deserialize(redisManager.get(key));
                sessions.add(s);
            }
        }

        return sessions;*/
        return new ArrayList<>();
    }

    private byte[] getByteKey(Serializable sessionId){
        String preKey = this.keyPrefix + sessionId;
        return preKey.getBytes();
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

}
