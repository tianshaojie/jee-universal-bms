/**
 * Created by tiansj on 15/7/12.
 * common层提供可复用的组件，供page层使用。
 */

/**
 * JQuery扩展方法，用户对JQuery EasyUI的DataGrid控件进行操作。
 */
$.fn.extend({
    /**
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
    }
});

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