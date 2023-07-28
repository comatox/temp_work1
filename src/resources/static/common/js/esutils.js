/**
 * replaceAll
 */
String.prototype.replaceAll = function (oldStr, newStr) {
  if ((this)) {
    return this.split(oldStr).join(newStr);
  } else {
    return this;
  }
};

(function ($) {
  $.esutils = $.esutils || {};
  const _ = $.esutils;
  const $this = this;

  // call api function (ref by getApi, postApi)
  _.callApi = function (method = "post", url, param, fnCallback, options = {}) {
    if (!url) {
      return;
    }

    // default options
    const _options = {
      contentType: 'application/json',
      url: global.contextPath + url,
      data: method.toLowerCase() === "post" && options.enctype !== "multipart/form-data" ? JSON.stringify(param) : param,
      type: method,
      dataType: "json",
      async: true,
      traditional: true,
      beforeSend: function () {
        if (options.loading === true) {
          _.loading(true);
        }
      },
      success: function (data) {
        if (options.loading === true) {
          _.loading(false);
        }
        return fnCallback && fnCallback(data);
      },
      error: function (XMLHttpRequest, textStatus) {
        if (options.loading === true) {
          _.loading(false);
        }
        alert(textStatus);
      }
    }
    // merge options
    $.extend(_options, options);
    // call api
    return $.ajax(_options);
  };

  /****************************************************************
   * getApi : GET api ajax 요청 (공통)
   *  - url : 요청 url
   *  - param : 요청 파라미터
   *  - fnCallback : callback function
   *  - options : (optional) ajax 설정 변경 시
   ****************************************************************/
  _.getApi = function (url, param, fnCallback, options) {
    return _.callApi("get", url, param, fnCallback, {loading: false, ...options});
  };

  /****************************************************************
   * postApi : POST api ajax 요청 (공통)
   *  - url : 요청 url
   *  - param : 요청 파라미터
   *  - fnCallback : callback function
   *  - options : (optional) ajax 설정 변경 시
   ****************************************************************/
  _.postApi = function (url, param, fnCallback, options) {
    return _.callApi("post", url, param, fnCallback, options);
  };

  /****************************************************************
   * renderData : 데이터 출력 (view/form) (공통)
   *  - targetId : 적용 대상 영역 ID
   *  - url : 요청 url
   *  - fnCallback : callback function
   ****************************************************************/
  _.renderData = async function (targetId, urlOrObject, fnCallback, options) {
    let data = {};
    try {
      if (typeof urlOrObject === "string") {
        const result = await _.getApi(urlOrObject, null, null, options);
        data = result.data;
      } else if (typeof urlOrObject === "object") {
        data = urlOrObject;
      } else {
        throw new Error(urlOrObject);
      }
    } catch (e) {
      console.log("esutils.renderData url or object type error = ", e);
    }

    console.log('data >>> ', data);
    // view data 처리
    $("[view-data]", `#${targetId}`).each((_, el) => $(el).text((data && data[$(el).attr("view-data")]) || ''));
    // form data 처리
    $(":input", `#${targetId}`).each((_, el) => {
      if (!options || !options.exclude || !options.exclude.includes($(el).attr("name"))) {
        const type = $(el).attr("type");
        const value = (data && data[$(el).attr("name")]) || '';

        if (type === "radio" || type === "checkbox") {
          $(el).filter(`[value=${value}]`).prop("checked", true);
        } else {
          $(el).val(value);
        }
      }
    });

    fnCallback && fnCallback(data);
  };

  /****************************************************************
   * loading : Global Loading 처리 (공통)
   *  - flag : 출력 여부 (true / false)
   ****************************************************************/
  _.loading = function (flag) {
    if (flag) {
      $("#globalLoading").show();
      setTimeout(() => $("#globalLoading").addClass("on"), 30);
    } else {
      $("#globalLoading").hide();
      $("#globalLoading").removeClass("on");
    }
  };

  /****************************************************************
   * openPopup : Window Popup 출력 (공통)
   *  - url : popup url
   *  - param : query parameter
   *  - name : popup name
   *  - width : popup width
   *  - height : popup height
   *  - scroll : 스크롤 여부 (Y/N)
   *  - fnCallback : callback function (전체 완료 후 호출)
   ****************************************************************/
  _.openPopup = (
      {
        url,
        param,
        name = "esecurity",
        width,
        height,
        scroll,
        fnCallback
      }
  ) => {
    let page = global.contextPath + url;
    if (param) {
      page += "?";
      let cnt = 0;
      $.each(param, function (index, value) {
        if (cnt > 0) {
          page += "&";
        }
        if (value != "") {
          page += index + "=" + value;
          cnt++;
        }
      });
    }
    const winl = (screen.width - width) / 2;
    const wint = (screen.height - height) / 2;
    const winprops = "height=" + height + ",width=" + width
        + ",top=" + wint + ",left=" + winl + ",scrollbars=" + scroll
        + ",resizable=no,toolbar=no,location=no,status=no";
    const win = window.open(page, name, winprops);
    win.fnParentCallback = () => {
    };

    if (parseInt(navigator.appVersion) >= 4) {
      win.window.focus();
    }

    if (fnCallback) {
      win.onload = () => {
        win.fnParentCallback = fnCallback;
      };
    }
  };

  /****************************************************************
   * openEmpPopup : 사원검색 Popup 출력 (공통)
   *  - empNm : 사원명
   *  - empId : 사번
   *  - compId : 회사코드
   *  - fnCallback : callback function (전체 완료 후 호출)
   ****************************************************************/
  _.openEmpPopup = ({
    empNm = "",
    empId = "",
    compId = "",
    fnCallback
  }) => {

    const param = {
      empId: empId ?? "",
      empNm: empNm ?? "",
      compId: compId ?? ""
    };

    _.openPopup({
      url: "/popup/findemployee",
      param,
      width: "650",
      height: "485",
      fnCallback
    });
  }

  /****************************************************************
   * openEmpPopup : 외부인 보안 위규자 검색 Popup 출력 (공통)
   *  - empNm : 사원명
   *  - compNm : 회사명
   *  - onedaySubcontYn : 도급업체 회원목록 조회 (Y/N)
   *  - fnCallback : callback function (전체 완료 후 호출)
   ****************************************************************/
  _.openIoEmpSearchViolationPopup = ({
    empNm = "",
    compNm = "",
    onedaySubcontYn,
    fnCallback
  }) => {

    const param = {
      compNm: compNm ?? "",
      empNm: empNm ?? "",
      onedaySubcontYn: onedaySubcontYn ?? "N"
    };

    _.openPopup({
      url: "/popup/findioemployeeviolation",
      param,
      width: "650",
      height: "485",
      fnCallback
    });
  }

  /****************************************************************
   * getCode : 코드 데이터 조회 (공통)
   *  - grpCd : 그룹코드 (string / array)
   *  - fnCallback : callback function (전체 완료 후 호출)
   ****************************************************************/
  _.getCode = async function (grpCd, fnCallback) {
    if (grpCd) {
      if (Array.isArray(grpCd)) {
        // multiple
        let results;
        try {
          results = await Promise.all(
              grpCd.map(
                  d => _.callApi("get", `/api/common/code/${d}`, {})));
        } catch (e) {
          console.log("esutils.getCode(multiple) error = ", e);
        }
        if (results) {
          fnCallback && fnCallback(
              Object.fromEntries(results.map((d, i) => [grpCd[i], d.data])));
        }
      } else {
        // single
        let result;
        try {
          result = await _.callApi("get", `/api/common/code/${grpCd}`, {});
        } catch (e) {
          console.log("esutils.getCode(single) error = ", e);
        }
        fnCallback && fnCallback(result.data);
      }
    }
  };

  /****************************************************************
   * renderCodeOptions : 코드 Option Element 출력
   *  - renderOptions : render options
   *    (type: 'select'/'radio' / grpCd: 그룹코드 / targetId: render target id / name: name tag value(radio only))
   *  - fnCallback : callback function (전체 완료 후 호출)
   *  - filter : code data filter
   ****************************************************************/
  _.renderCode = async function (renderOptions, fnCallback) {
    if (Array.isArray(renderOptions)) {
      let results;
      try {
        results = await Promise.all(
            renderOptions.map(
                d => _.callApi("get", d.url || `/api/common/code/${d.grpCd}`, d.param)));
        _.renderInputs(renderOptions, results, fnCallback);
      } catch (e) {
        console.log("esutils.renderCode error = ", e);
      }
    }
  };

  _.renderInputs = function (renderOptions, renderData, fnCallback) {
    if (renderData) {
      renderData.map((d, i) => {
        const {type, name, targetId, nameProp = "detlNm", valueProp = "detlCd", filter, blankOption} = renderOptions[i];
        let _html = "";
        let _data = d.data;

        if (filter) {
          _data = _data.filter(filter);
        }

        if (type === "select") {
          if (blankOption) {
            _html += `<option value="">${blankOption === true ? "::: 선택하세요 :::" : blankOption}</option>`;
          }
          $.each(_data, function (_, data) {
            const _name = data[nameProp];
            const _value = data[valueProp];
            _html += `<option value="${_value}">${_name}</option>`;
          });
        } else if (type === "radio") {
          $.each(_data, function (_, data) {
            const _value = data[valueProp];
            _html += `<input type="radio" name="${name}" id="${name}_${_value}" value="${_value}"/><label for="${name}_${_value}">${data.detlNm}</label>`;
          });
        }
        $("#" + targetId).html(_html);
      });

      fnCallback && fnCallback(renderData);
    }
  }

  /****************************************************************
   * setMenuList : 사용자 메뉴 목록 설정 (async = false) / 최초 1회 로딩
   *  - empId : 사용자ID
   *  - currentMenuId : 현재 메뉴ID
   ****************************************************************/
  _.setMenuList = function (empId, currentMenuId, originalMenuId) {
    _.callApi("get", `/api/common/menu/${empId}`, {}, function (result) {
      if (result && result.data) {
        const currentMenuInfo = result.data.find(
            d => d.menuId === currentMenuId);
        console.log('currentMenuInfo>>>', currentMenuInfo)
        let depth1;
        let depth2;
        let depth3;
        if (currentMenuInfo) {
          if (currentMenuInfo.depth === 2) {
            depth2 = result.data.find(
                d => d.menuId === currentMenuInfo.menuId);
          } else {
            depth3 = result.data.find(
                d => d.menuId === (currentMenuInfo.depth === 3
                    ? currentMenuInfo.menuId : currentMenuInfo.upMenuId));
            if (depth3) {
              depth2 = result.data.find(
                  d => d.menuId === depth3.upMenuId);
            }
          }
          if (depth2) {
            depth1 = result.data.find(
                d => d.menuId === depth2.upMenuId);
          }
        }

        global.menuInfo = {
          list: result.data,
          current: {
            menuId: currentMenuId,
            originalMenuId,
            depth1,
            depth2,
            depth3,
          }
        };
      }
    }, {async: false});
  }

  /****************************************************************
   * href : 화면 이동
   *  - url : 이동 할 url
   ****************************************************************/
  _.href = function (url, param) {
    let queryString = "";
    if (param) {
      queryString = "?" + Object.entries(param).map(([k, v]) => `${k}=${v}`).join("&");
    }
    window.location.href = `${global.contextPath}${url}${queryString}`;
  }

  /****************************************************************
   * getToday : 현재일자 문자열 조회
   *  - delimiter : 구분자
   ****************************************************************/
  _.getToday = function (delimiter) {
    if (delimiter == null || delimiter == undefined) {
      delimiter = "-";
    }

    const date = new Date();
    const yyyy = date.getFullYear();
    let mm = date.getMonth() + 1;
    let dd = date.getDate();

    if (mm < 10) {
      mm = "0" + mm;
    }
    if (dd < 10) {
      dd = "0" + dd;
    }

    return yyyy + delimiter + mm + delimiter + dd;
  }

  /****************************************************************
   * getDaysBetweenMonths : 기간 일수 조회 (from~to 일자 계산)
   *  - startDt : 시작일자 (string)
   *  - endDt : 종료일자 (string)
   *  - delimiter : 구분자
   ****************************************************************/
  _.getDaysBetweenMonths = function (startDt, endDt, delimiter) {
    if (isNull(delimiter)) {
      delimiter = "-";
    }
    startDt = startDt.replaceAll(delimiter, "");
    endDt = endDt.replaceAll(delimiter, "");

    const startDtArr = new Array();
    startDtArr[0] = parseInt(startDt.substring(0, 4), 10);
    startDtArr[1] = parseInt(startDt.substring(4, 6), 10) - 1;
    startDtArr[2] = parseInt(startDt.substring(6), 10);
    const endDtArr = new Array();
    endDtArr[0] = parseInt(endDt.substring(0, 4), 10);
    endDtArr[1] = parseInt(endDt.substring(4, 6), 10) - 1;
    endDtArr[2] = parseInt(endDt.substring(6), 10);

    const _startDt = new Date(startDtArr[0], startDtArr[1], startDtArr[2]);
    const _endDt = new Date(endDtArr[0], endDtArr[1], endDtArr[2]);
    const days = (_endDt.getTime() - _startDt.getTime()) / 1000 / 60 / 60 / 24;

    return Math.abs(days);
  }

  /****************************************************************
   * datepickerLocale : DatePicker 언어 설정
   ****************************************************************/
  _.datepickerLocale = {
    days: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
    daysShort: ['일', '월', '화', '수', '목', '금', '토'],
    daysMin: ['일', '월', '화', '수', '목', '금', '토'],
    months: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthsShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    today: '오늘',
    clear: '초기화',
    dateFormat: 'yyyy-MM-dd',
    firstDay: 0
  }

  /****************************************************************
   * datepicker : DatePicker (single)
   *  - targets : target selector ([array)
   *  - options : custom options
   ****************************************************************/
  _.datepicker = function (targets, options) {
    let result = [];
    if (targets) {
      result = targets.map(target => {
        const _target = new AirDatepicker(target, {
          locale: _.datepickerLocale,
          autoClose: true,
          ...options
        });
        $(target).on("change", function () {
          const date = new Date($(this).val());
          _target.selectDate(date);
          _target.setViewDate(date);
        });
        if ($(target).val()) {
          $(target).trigger("change");
        }
        return _target;
      })
    }
    return result;
  }

  /****************************************************************
   * rangepicker : DatePicker (range)
   *  - targets : target selector ([[array],[array]])
   *  - options : custom options
   ****************************************************************/
  _.rangepicker = function (targets, options) {
    let result = [];
    if (targets) {
      result = targets.map(([start, end]) => {
        let _start, _end;
        _start = new AirDatepicker(start, {
          locale: _.datepickerLocale,
          autoClose: true,
          onSelect({date}) {
            if (date) {
              _end.update({minDate: date})
            }
          },
          maxDate: $(end).val(),
          ...options
        });
        _end = new AirDatepicker(end, {
          locale: _.datepickerLocale,
          autoClose: true,
          onSelect({date}) {
            if (date) {
              _start.update({maxDate: date})
            }
          },
          minDate: $(start).val(),
          ...options
        });

        $(start).on("change", function () {
          const date = new Date($(this).val());
          _start.selectDate(date);
          _start.setViewDate(date);
        });
        $(end).on("change", function () {
          const date = new Date($(this).val());
          _end.selectDate(date);
          _end.setViewDate(date);
        });
        return {start: _start, end: _end};
      });
    }
    return result;
  }

  /****************************************************************
   * getFieldsValue : get form values (object)
   *  - form : jquery form object (ex> $.esutils.getFieldsValue($("#form));)
   ****************************************************************/
  _.getFieldsValue = (form) => {
    const forms = form.serializeArray();
    return forms ? forms.reduce((acc, cur) => ({...acc, [cur.name]: cur.value}), {}) : null;
  };

  /****************************************************************
   * multipartSubmit : form 전체를 submit 한다 (multipart 타입)
   ****************************************************************/
  _.multipartSubmit = function(url, formObject, beforeSend, fnCallback){
    const options = {
      processData: false,
      contentType: false,
      enctype: 'multipart/form-data',
    };

    let formData = new FormData(formObject)

    if(beforeSend){
      formData = beforeSend(formData);
    }

    return _.callApi("post", url, formData, fnCallback, options);
  };

  /****************************************************************
   * fileDownload : 파일 다운로드
   *  - filePath : 전체파일경로
   *    (ex: C:\\uploadFile\\bbs\\202307\\BBS_2023079417d3ba-95bc-4a9e-884e-5f2879306b2d.jpg )
   *  - fileName : 오리지날 파일이름 (확장자포함)
   *    (ex: test.jpg)
   ****************************************************************/
  _.fileDownload = function (filePath, fileName) {
    const xhr = new XMLHttpRequest();
    const encodeFilePath = encodeURIComponent(filePath);
    xhr.open('GET', global.contextPath + '/api/common/fileDownload?filePath=' + encodeFilePath, true);
    xhr.responseType = 'blob';
    xhr.onload = function () {
      if (xhr.status === 200) {
        // 파일 다운로드를 위한 URL 생성
        const downloadUrl = URL.createObjectURL(xhr.response);

        // 링크 생성 및 클릭
        const link = document.createElement('a');
        link.href = downloadUrl;
        link.download = fileName;
        link.style.display = 'none';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      } else {
        alert('파일 다운로드 오류');
      }
    };
    xhr.send();
  };

  /****************************************************************
   * checkAuth : 사용자 권한 체크
   * ex> $.esutils.checkAuth("1", "<%=login.get("AUTH")%>");
   *  - authId : 체크할 권한ID
   *  - userAuth : 세션의 AUTH 목록 (string)
   ****************************************************************/
  _.checkAuth = function (authId, userAuth) {
    let result = false;
    if (userAuth && authId) {
      const userAuthList = userAuth.replace("[", "").replace("]", "").split(",").map(d=>d.trim());
      result = userAuthList.includes(authId);
    }
    return result;
  };

  /****************************************************************
   * getUrlParam : 쿼리스트링 Json object로 파싱
   * ex> $.esutils.getUrlParam(url);
   *  - url : (optional)
   ****************************************************************/
  _.getUrlParam = function (queryString = location.search ?? "") {
    if(typeof queryString !== "string") return null;

    let urlParam = '';
    urlParam = queryString.substring(queryString.indexOf('?')+1);

    return urlParam.split("&").map(v=>v.split('=')).reduce((acc,value)=>{
      const [key, val] = value;
      acc[key]=val;
      return acc;
    },{});
  }

  /****************************************************************
   * setComma : 숫자 콤마 추가
   *  - val : 콤마를 붙일 숫자
   ****************************************************************/
  _.setComma = function (val) {
    const reg = /(^[+-]?\d+)(\d{3})/;
    val += '';
    while (reg.test(val)) {
      val = val.replace(reg, '$1' + ',' + '$2');
    }
    return val;
  };

  /****************************************************************
   * unSetComma : 숫자 콤마 제거
   *  - val : 콤마를 붙일 숫자
   ****************************************************************/
  _.unSetComma = function (val) {
    return Number(val.replace(/,/g, ""));
  };
})(jQuery);

/**
 * 문자열의 바이트수 리턴
 */
String.prototype.getBytes = function () {
  var l = 0;

  for (var idx = 0; idx < this.length; idx++) {
    var c = escape(this.charAt(idx));

    if (c.length == 1) {
      l++;
    } else if (c.indexOf("%u") != -1) {
      l += 2;
    } else if (c.indexOf("%") != -1) {
      l += c.length / 3;
    }
  }

  return l;
};

/**
 * 문자열의 바이트수 리턴 : DB내의 한글Byte를 3Byte로 계산해서 적용함
 */
String.prototype.getBytes3 = function () {
  var l = 0;

  for (var idx = 0; idx < this.length; idx++) {
    var c = escape(this.charAt(idx));

    if (c.length == 1) {
      l++;
    } else if (c.indexOf("%u") != -1) {
      l += 3;
    } else if (c.indexOf("%") != -1) {
      l += c.length / 3 + 1;
    }
  }

  return l;
}
