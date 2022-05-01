function navBar(strData) {
  //console.log(strData)
  //strData是左侧菜单列表
  var data;
  if (typeof (strData) === "string") {
    var data = JSON.parse(strData); //部分用户解析出来的是字符串，转换一下
  } else {
    data = strData;
  }

  var curmenu = window.sessionStorage.getItem("curmenu"), curmenuObj;
  //console.log(curmenu)
  if (curmenu && curmenu != "undefined") {
    curmenuObj = JSON.parse(window.sessionStorage.getItem("curmenu"));
	//document.getElementById("rightFrameFocus").src=curmenuObj.href; //刷新时动态设置src属性
  }
  var ulHtml = '<ul class="layui-nav layui-nav-tree">';

  function contains(arr, str) {
    for (let i = 0; i < arr.length; i++) {
      if (arr[i].href.indexOf(str) > -1){ 
		return true
	  }
    }
    return false
  }
  
  
  for (var i = 0; i < data.length; i++) {
	//当前选中的menu样式
    if (curmenu && curmenu != "undefined") {
      for (var j = 0; j < data[i].children.length; j++) {
        if (contains(data[i].children, curmenuObj.href)) {
          ulHtml += '<li class="layui-nav-item layui-nav-itemed">';
        } else {
          ulHtml += '<li class="layui-nav-item">';
        }
      }
    } 
	//其他menu的样式
	else {
      if (i == 0) {
        ulHtml += '<li class="layui-nav-item layui-nav-itemed">';
      } else {
        ulHtml += '<li class="layui-nav-item">';
      }
    }
	
	//子menu的样式
    if (data[i].children !== undefined && data[i].children.length > 0) {
      ulHtml += '<a href="javascript:;">';
      if (data[i].icon !== undefined && data[i].icon != '') {
        if (data[i].icon.indexOf("icon-") !== -1) {
          ulHtml += '<i class="iconfont ' + data[i].icon + '" style="font-size: 20px" data-icon="' + data[i].icon + '"></i>';
        } else {
          ulHtml += '<i class="layui-icon" style="font-size: 20px" data-icon="' + data[i].icon + '">' + data[i].icon + '</i>';
        }
      }
      ulHtml += '<cite>' + data[i].title + '</cite>';
      ulHtml += '<span class="layui-nav-more"></span>';
      ulHtml += '</a>';
      ulHtml += '<dl class="layui-nav-child">';
      for (var j = 0; j < data[i].children.length; j++) {
        // if (data[i].children[j].target === "_blank") {
        //   ulHtml += '<dd><a href="javascript:;" style="font-size: 20px" data-url="' + data[i].children[j].href + '" target="' + data[i].children[j].target + '">';
        // } else {
        //   ulHtml += '<dd><a href="javascript:;" style="font-size: 20px" data-url="' + data[i].children[j].href + '">';
        // }
        if (curmenu && curmenu != "undefined") {
          if (data[i].children[j].target === "_blank" && data[i].children[j].href == curmenuObj.href) {
            ulHtml += '<dd class="layui-this"><a href="javascript:;" style="font-size: 20px" data-url="' + data[i].children[j].href + '" target="' + data[i].children[j].target + '">';
          } else if (data[i].children[j].target === "_blank" && data[i].children[j].href != curmenuObj.href) {
            ulHtml += '<dd><a href="javascript:;" style="font-size: 20px" data-url="' + data[i].children[j].href + '" target="' + data[i].children[j].target + '">';
          } else if (data[i].children[j].target !== "_blank" && data[i].children[j].href == curmenuObj.href) {
            ulHtml += '<dd class="layui-this"><a href="javascript:;" style="font-size: 20px" data-url="' + data[i].children[j].href + '">';
          } else {
            ulHtml += '<dd><a href="javascript:;" style="font-size: 20px" data-url="' + data[i].children[j].href + '">';
          }
        } else {
          if (data[i].children[j].target === "_blank" && i == 0 && j == 3) {
            ulHtml += '<dd class="layui-this"><a href="javascript:;" style="font-size: 20px" data-url="' + data[i].children[j].href + '" target="' + data[i].children[j].target + '">';
          } else if (data[i].children[j].target === "_blank" && j != 3) {
            ulHtml += '<dd><a href="javascript:;" style="font-size: 20px" data-url="' + data[i].children[j].href + '" target="' + data[i].children[j].target + '">';
          } else if (data[i].children[j].target !== "_blank" && i == 0 && j == 3) {
            ulHtml += '<dd class="layui-this"><a href="javascript:;" style="font-size: 20px" data-url="' + data[i].children[j].href + '">';
          } else {
            ulHtml += '<dd><a href="javascript:;" style="font-size: 20px" data-url="' + data[i].children[j].href + '">';
          }
        }
        if (data[i].children[j].icon !== undefined && data[i].children[j].icon !== '') {
          if (data[i].children[j].icon.indexOf("icon-") !== -1) {
            ulHtml += '<i class="iconfont ' + data[i].children[j].icon + '" style="font-size: 20px" data-icon="' + data[i].children[j].icon + '"></i>';
          } else {
            ulHtml += '<i class="layui-icon" style="font-size: 20px" data-icon="' + data[i].children[j].icon + '">' + data[i].children[j].icon + '</i>';
          }
        }
        ulHtml += '<cite>' + data[i].children[j].title + '</cite></a></dd>';
      }
      ulHtml += "</dl>";
    } else {
      if (data[i].target === "_blank") {
        ulHtml += '<a href="javascript:;" data-url="' + data[i].href + '" target="' + data[i].target + '">';
      } else {
        ulHtml += '<a href="javascript:;" data-url="' + data[i].href + '">';
      }
      if (data[i].icon !== undefined && data[i].icon !== '') {
        if (data[i].icon.indexOf("icon-") !== -1) {
          ulHtml += '<i class="iconfont ' + data[i].icon + '" style="font-size: 20px" data-icon="' + data[i].icon + '"></i>';
        } else {
          ulHtml += '<i class="layui-icon" style="font-size: 20px" data-icon="' + data[i].icon + '">' + data[i].icon + '</i>';
        }
      }
      ulHtml += '<cite>' + data[i].title + '</cite></a>';
    }
    ulHtml += '</li>';
  }
  ulHtml += '</ul>';

  return ulHtml;
}