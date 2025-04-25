
/**
 *  普通的数据格式
 * @param method    接口名称
 * @param param     参数名称
 * @param retype    返回类型--text---json
 */
function doPost(method,param,succ,retype) {
    $.post(method,param,function (data) {
        succ(data)
    },retype)
}

/**
 *  还有文件格式
 * @param method    接口名称
 * @param param     参数名称
 * @param retype    返回类型--text---json
 */
function doPostFile(method,param,succ,retype) {
    $.ajax({
        url:method,
        type:'post',
        data:param,
        contentType:false,
        async: false,
        cache: false,
        processData: false,
        dataType:retype,
        success:function (data) {
            succ(data)
        }
    })
}