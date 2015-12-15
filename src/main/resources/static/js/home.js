/**
 * Created by tiansj on 15/6/25.
 * home.html页面使用
 */
function addTab(title, url, iconCls){
    if ($('#tabs').tabs('exists', title)) {
        $('#tabs').tabs('select', title);//选中并刷新
        var currTab = $('#tabs').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        if (url != undefined && currTab.panel('options').title != 'Home') {
            $('#tabs').tabs('update', {
                tab: currTab,
                options: {
                    content: createFrame(url)
                }
            })
        }
    } else {
        var content = createFrame(url);
        $('#tabs').tabs('add', {
            title: title,
            content: content,
            iconCls: iconCls,
            closable: true
        });
    }
    tabClose();
    //$('#demo').panel({
    //    href:'/home.html',
    //    onLoad:function(){
    //        alert('loaded successfully');
    //    }
    //});
}
function createFrame(url) {
    return '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';


    //return "<div id=\"demo\" data-options=\"href:'basic.html',border:false\" style=\"padding:20px;overflow:hidden;min-height:350px\"></div>";
}

function tabClose() {
    /*双击关闭TAB选项卡*/
    $(".tabs-inner").dblclick(function(){
        var subtitle = $(this).children(".tabs-closable").text();
        $('#tabs').tabs('close',subtitle);
    })
    /*为选项卡绑定右键*/
    $(".tabs-inner").bind('contextmenu',function(e){
        $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
        });
        var subtitle =$(this).children(".tabs-closable").text();
        $('#mm').data("currtab",subtitle);
        $('#tabs').tabs('select',subtitle);
        return false;
    });
}

//绑定右键菜单事件
$(function() {
    //刷新
    $('#mm-tabupdate').click(function(){
        var currTab = $('#tabs').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        if(url != undefined && currTab.panel('options').title != 'Home') {
            $('#tabs').tabs('update',{
                tab:currTab,
                options:{
                    content:createFrame(url)
                }
            })
        }
    })
    //关闭当前
    $('#mm-tabclose').click(function(){
        var currtab_title = $('#mm').data("currtab");
        $('#tabs').tabs('close',currtab_title);
    })
    //全部关闭
    $('#mm-tabcloseall').click(function(){
        $('.tabs-inner span').each(function(i,n){
            var t = $(n).text();
            if(t != 'Home') {
                $('#tabs').tabs('close',t);
            }
        });
    });
    //关闭除当前之外的TAB
    $('#mm-tabcloseother').click(function(){
        var prevall = $('.tabs-selected').prevAll();
        var nextall = $('.tabs-selected').nextAll();
        if(prevall.length>0){
            prevall.each(function(i,n){
                var t=$('a:eq(0) span',$(n)).text();
                if(t != 'Home') {
                    $('#tabs').tabs('close',t);
                }
            });
        }
        if(nextall.length>0) {
            nextall.each(function(i,n){
                var t=$('a:eq(0) span',$(n)).text();
                if(t != 'Home') {
                    $('#tabs').tabs('close',t);
                }
            });
        }
        return false;
    });
    //关闭当前右侧的TAB
    $('#mm-tabcloseright').click(function(){
        var nextall = $('.tabs-selected').nextAll();
        if(nextall.length==0){
            //msgShow('系统提示','后边没有啦~~','error');
            alert('后边没有啦~~');
            return false;
        }
        nextall.each(function(i,n){
            var t=$('a:eq(0) span',$(n)).text();
            $('#tabs').tabs('close',t);
        });
        return false;
    });
    //关闭当前左侧的TAB
    $('#mm-tabcloseleft').click(function(){
        var prevall = $('.tabs-selected').prevAll();
        if(prevall.length==0){
            alert('到头了，前边没有啦~~');
            return false;
        }
        prevall.each(function(i,n){
            var t=$('a:eq(0) span',$(n)).text();
            $('#tabs').tabs('close',t);
        });
        return false;
    });

    //退出
    $("#mm-exit").click(function(){
        $('#mm').menu('hide');
    })

    var skins = $('.li-skinitem span').click(function() {
        var self = $(this);
        if(self.hasClass('cs-skin-on')) return;
        skins.removeClass('cs-skin-on');
        self.addClass('cs-skin-on');
        var skin = self.attr('rel');
        $('#swicth-style').attr('href', themes[skin]);
        $.cookie('cs-skin', skin, {expires: 365});

        var iframes = document.getElementsByTagName("iframe");
        for (var i = 0; i < iframes.length; i++) {
            var ifr = iframes[i];
            var win = ifr.window || ifr.contentWindow;
            win.updateSkin && win.updateSkin(); // 调用iframe中的a函数
        }
    });

    updateSkin();

    /*$('.easyui-accordion li a').click(function(){
        $('.easyui-accordion li div').removeClass("selected");
        var self = $(this);
        self.parent().addClass("selected");
        addTab(self.text(), self.attr('src'));
    }).hover(function(){
        $(this).parent().addClass("hover");
    },function(){
        $(this).parent().removeClass("hover");
    });*/
});

$(function() {
    $.ajax({
        type: "get",
        url: "/v1/api0/user/resource",
        data: {},
        success: function(data) {
            if(data.code != 200) {
                window.location = "/login.html";
                return;
            }
            var tree = data.body;
            $.each(tree, function(i, node) {
                $('#menu').accordion('add', {
                    title: node.text,
                    iconCls: node.iconCls,
                    content: $('<ul class="easyui-tree"/>').data('node', node.children)
                });
            });
            $('.easyui-tree').each(function(i, elem) {
                $(elem).tree('loadData', $(elem).data('node'));
                $(elem).tree({animate:true});
                $(elem).tree({onClick: function(node){
                    if($(elem).tree('isLeaf',node.target)){
                        addTab(node.text, node.attributes.url, node.iconCls);
                    }
                }
                });
            });
            $('#menu').accordion('select', 0);
        }

    });
});
