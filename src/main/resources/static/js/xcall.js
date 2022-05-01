//FileName的值为六种字符串'0','1','2','3','4','5',‘6’ 分别对应直接拨打工程师电话，接收录音、登门录音、回电录音、取件录音、换机录音，直接客户电话
async function send(CallNum, FileName) {
    var UID = getuid();
    var message = {'CallNum': CallNum, 'MsgType': 'Call', 'FileName': FileName, 'UID': UID};
    var ws = new WebSocket('ws://localhost:8910');
    ws.onopen = function (evt) {
        ws.send(JSON.stringify(message) + '\r');
    };
    ws.onerror = function () {
        layer.alert("与本地客户端连接失败，请检查线路");
    }
}

async  function sendL(LoginName, LoginMobile,ServiceName) {
    var UID = getuid();
    var message = {'LoginName': LoginName, 'MsgType': 'Login', 'LoginMobile': LoginMobile, 'ServiceName': ServiceName};
    var i = 80;
    var isSuccess = false;
    var ws = new WebSocket('ws://localhost:8910');
    ws.onopen = function (evt) {
        isSuccess = true;
        ws.send(JSON.stringify(message) + '\r');
        return true;
    };
    while (!isSuccess && i > 0) {
        i--;
        await sleep(1);
    }
    if (i == 0) {
        // layer.msg("与本地客户端连接失败，请检查线路");
        return false;
    }

}

//下面的函数不用管，send内部调用的
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function getuid() {
    function S4() {
        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
    }
    return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
}