/**
 * Created by tiansj on 15/7/12.
 * common层提供可复用的组件，供page层使用。
 */

(function($) {
    $.fn.extend({
        /**
         * JQuery EasyUI DataGrid 扩展方法
         * 修改DataGrid对象的默认大小，以适应页面宽度。
         * @param heightMargin 高度对页内边距的距离。
         * @param widthMargin 宽度对页内边距的距离。
         * @param minHeight 最小高度。
         * @param minWidth 最小宽度。
         */
        resizeDataGrid : function(heightMargin, widthMargin, minHeight, minWidth) {
            var height = $(document.body).height() - heightMargin;
            var width = $(document.body).width() - widthMargin;
            height = height < minHeight ? minHeight : height;
            width = width < minWidth ? minWidth : width;
            $(this).datagrid('resize', {
                height : height,
                width : width
            });
        },

        /**
         * JQuery EasyUI TreeGrid 扩展方法
         * 修改TreeGrid对象的默认大小，以适应页面宽度。
         * @param heightMargin 高度对页内边距的距离。
         * @param widthMargin 宽度对页内边距的距离。
         * @param minHeight 最小高度。
         * @param minWidth 最小宽度。
         */
        resizeTreeGrid : function(heightMargin, widthMargin, minHeight, minWidth) {
            var height = $(document.body).height() - heightMargin;
            var width = $(document.body).width() - widthMargin;
            height = height < minHeight ? minHeight : height;
            width = width < minWidth ? minWidth : width;
            $(this).treegrid('resize', {
                height : height,
                width : width
            });
        },

        /**
         * Form 表单序列化为JSON对象
         * 使用方法：$('#ffSearch').serializeObject()
         * @returns {{}}
         */
        serializeObject : function() {
            var o = {};
            var a = this.serializeArray();
            $.each(a, function() {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        }
    });


    /**
     * JQuery EasyUI ValidateBox 扩展方法
     */
    $.extend($.fn.validatebox.defaults.rules, {
        minLength: {
            validator: function(value, param){   //value 为需要校验的输入框的值 , param为使用此规则时存入的参数
                return value.length >= param[0];
            },
            message: '请输入最小{0}位字符.'
        }
    });

    $.extend($.fn.validatebox.defaults.rules, {
        maxLength: {
            validator: function(value, param){
                return param[0] >= value.length;
            },
            message: '请输入最大{0}位字符.'
        }
    });

    $.extend($.fn.validatebox.defaults.rules, {
        length: {
            validator: function(value, param){
                return value.length >= param[0] && param[1] >= value.length;
            },
            message: '请输入{0}-{1}位字符.'
        }
    });

    // extend the 'equals' rule
    $.extend($.fn.validatebox.defaults.rules, {
        equals: {
            validator: function(value,param){
                return value == $(param[0]).val();
            },
            message: '字段不相同.'
        }
    });

    $.extend($.fn.validatebox.defaults.rules, {
        web : {
            validator: function(value){
                return /^(http[s]{0,1}|ftp):\/\//i.test($.trim(value));
            },
            message: '网址格式错误.'
        }
    });

    $.extend($.fn.validatebox.defaults.rules, {
        mobile : {
            validator: function(value){
                return /^1[0-9]{10}$/i.test($.trim(value));
            },
            message: '手机号码格式错误.'
        }
    });

    $.extend($.fn.validatebox.defaults.rules, {
        date : {
            validator: function(value){
                return /^[0-9]{4}[-][0-9]{2}[-][0-9]{2}$/i.test($.trim(value));
            },
            message: '曰期格式错误,如2012-09-11.'
        }
    });

    $.extend($.fn.validatebox.defaults.rules, {
        email : {
            validator: function(value){
                return /^[a-zA-Z0-9_+.-]+\@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/i.test($.trim(value));
            },
            message: '电子邮箱格式错误.'
        }
    });

    $.extend($.fn.validatebox.defaults.rules, {
        captcha : {
            validator: function(value){
                var data0 = false;
                $.ajax({
                    type: "POST",async:false,
                    url:contextPath + "/json/valSimulation.action",
                    dataType:"json",
                    data:{"simulation":value},
                    async:false,
                    success: function(data){
                        data0=data;
                    }
                });

                return data0;
// 			        	return /^[a-zA-Z0-9]{4}$/i.test($.trim(value));
            },
            message: '验证码错误.'
        }
    });

    $.extend($.fn.validatebox.defaults.rules, {
        txtName : {
            validator: function(value,param){
                var data0 = false;
                if(value.length >= param[0] && param[1] >= value.length)
                {
                    $.ajax({
                        type: "POST",async:false,
                        url:contextPath + "/json/valName.action",
                        dataType:"json",
                        data:{"txtName":value},
                        async:false,
                        success: function(data){
                            data0=!data;
                        }
                    });
                }else{
                    param[2] = "请输入"+param[0]+"-"+param[1]+"位字符.";
                    return false;
                }

                param[2] = "用户名称已存在.";
                return data0;
            },
            message: "{2}"
        }
    });
})(window.jQuery);


// 全局皮肤模版
var themes = {
    'gray' : '/themes/gray/easyui.css',
    'bootstrap' : '/themes/bootstrap/easyui.css',
    'default' : '/themes/default/easyui.css',
    'metro' : '/themes/metro/easyui.css'
};

// 更新皮肤
function updateSkin() {
    if($.cookie('cs-skin')) {
        var skin = $.cookie('cs-skin');
        $('#swicth-style').attr('href', themes[skin]);
        var self = $('.li-skinitem span[rel='+skin+']');
        self.addClass('cs-skin-on');
    }
}

/**
 * 显示提示信息
 * @param msg
 */
function showMessage(msg){
    msg = msg || "操作成功！"
    $.messager.show({
        title:'提示',
        msg:msg,
        timeout:4000,
        showType:'slide',
        style:{
            right:'',
            top:document.body.scrollTop+document.documentElement.scrollTop,
            bottom:''
        }
    });
}

/**
 日期格式化：
 var date = new Date(long time);
 date.Format("yyyy-MM-dd");
 date.Format("yyyy-MM-dd HH:mm:ss");
 var weekStr = "星期" + date.Format("w");
 */
Date.prototype.Format = function(formatStr)
{
    var str = formatStr;
    var Week = ['日','一','二','三','四','五','六'];

    str=str.replace(/yyyy|YYYY/,this.getFullYear());
    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));

    str=str.replace(/MM/, (this.getMonth() + 1) > 9 ? (this.getMonth() + 1).toString():'0' + (this.getMonth() + 1));
    str=str.replace(/M/g, (this.getMonth() + 1));

    str=str.replace(/w|W/g,Week[this.getDay()]);

    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());
    str=str.replace(/d|D/g,this.getDate());

    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());
    str=str.replace(/h|H/g,this.getHours());
    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());
    str=str.replace(/m/g,this.getMinutes());

    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());
    str=str.replace(/s|S/g,this.getSeconds());

    return str;
}

function formatDateTime(value){
    if (value) {
        return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
    }
    return "";
}

function formatDate(value){
    if (value) {
        return new Date(value).Format("yyyy-MM-dd");
    }
    return "";
}




