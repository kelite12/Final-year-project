function getStatus(status) {
    var result = "未预订"
    switch (status) {
        case 0:
            result = "未预订"
            break;
        case 1:
            result = "已预订"
            break;
    }
    return result;
}

function getRoomType(type) {
    var result = "大床房"
    switch (type) {
        case 0:
            result = "大床房"
            break;
        case 1:
            result = "标准间"
            break;
        case 2:
            result = "情侣房"
            break;
        case 3:
            result = "商务房"
            break;
    }
    return result;
}
// movie
function getMovieStatus(status) {
    var result = "未开始"
    switch (status) {
        case 0:
            result = "未开始"
            break;
        case 1:
            result = "已开始"
            break;
    }
    return result;
}