class EsecurityApproval {
  constructor(targetId) {
    this.targetId = targetId;
    this._targetId = `#${this.targetId}`;
    this.$targetEl = $(this._targetId);
    this.options = {};
  }

  form(options = {}) {
    if (!this.$targetEl[0]) {
      return;
    }

    // set options
    this.options = options;
    // set params
    this.setParam();

    // generate template
    this.$targetEl.html(this.generateTemplate());

    this.requestList = [];
    this.permitList = [];
    this.savedRequestList = [];
    this.savedPermitList = [];

    if(options.initFetch === false) {
      this.renderApprLine("1", []);
      this.renderApprLine("2", []);
    } else {
      // render request line (by appr_def)
      this.renderApprLineByApi("1");
      // render permit line (by appr_def)
      this.renderApprLineByApi("2");
    }

    // if (this.param.docId) this.getSavedApprLine(this.param.docId);

    // bind events
    this.bindDefaultEvents();
  }

  view(docId) {
    if (!this.$targetEl[0]) {
      return;
    }

    this.param = {
      docId
    }

    this.apprTextInfo = {
      "1": {
        resultNm: "승인",
        classNm: "approve",
        entrustIdCol: "RETR_EMP_ID",
      },
      "2": {
        resultNm: "반려",
        classNm: "rejected",
        entrustIdCol: "RETR_EMP_ID",
      },
      "0": {
        resultNm: "준비",
        classNm: "signbefore",
        entrustIdCol: "ENTRUS_EMP_ID",
      },
      "before": {
        resultNm: "준비",
        classNm: "signbefore",
        entrustIdCol: "ENTRUS_EMP_ID",
      }
    }

    // generate template
    this.$targetEl.html(this.generateTemplateView());
    if (this.param.docId) {
      this.renderView();
    }
  }

  setParam() {
    this.param = {
      menuId: global.menuInfo.current.originalMenuId,
      compId: global.compId,
      divCd: global.divCd,
      deptId: global.deptId,
      deptNm: global.deptNm,
      jcId: global.jcCd,
      jwNm: global.jwNm,
      empId: global.empId,
      empNm: global.empNm,
      acIp: global.acIp,
      ...this.options.param
    }
  }

  generateTemplate() {
    const template = `
        <h1 class="txt_title01 fl">결재절차 <span class="descript">(결재자 선택 순서대로 상신)</span></h1>
        <div class="sign_descript">
            <ul>
                <li class="sign01">승인</li>
                <li class="sign02">부결</li>
                <li class="sign03">준비</li>
            </ul>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="view_board">
            <colgroup>
            <col width="151" />
            <col width="" />
            </colgroup>
            <tbody>
                <tr>
                    <th class="requestLineTh">요청부서</th>
                    <td class="requestLineTd"></td>
                </tr>
                <tr>
                    <th class="permitLineTh">허가부서</th>
                    <td class="permitLineTd"></td>
                </tr>
                <tr>
                    <th class="apprEtcTd">비고</th>
                    <td class="canceletcTd">
                        <div class="approve_board">
                        <div class="approve_reg">
                        <div class="approve_input">
                        <textarea name="canceletc" class="approve_textarea"></textarea>
                        </div>
                        </div>
                        <ul class="approve_list canceletc_div" style="display:none;"></ul>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
        <div id="high_select">
            <p class="all_open"></p>
            <div class="select_content">
                <div class="sign_left">
                    <h2 class="txt_title02">요청부서 결재선 지정 안내</h2>
                    <p><b>※결재선 지정에 어긋날 경우 반려됩니다.</b><br><b>※차상위자에게 승인 신청 바랍니다.</b></p>
                    <div style="height: 185px;" class="table_type3 scroll">
                        <table cellpadding="0" cellspacing="0" caption="요청부서" border="0" class="requestListTable">
                            <caption>요청부서</caption>
                            <colgroup>
                                <col width="10%" />
                                <col width="90%" />
                            </colgroup>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                    <p class="fl">
                        <span class="button bt2"><button type="button" class="setRequestApprLine">요청부서 결재선 지정</button></span>
                        <span class="button bt2"><button type="button" class="apprAllCancle">모두취소</button></span>
                    </p>
                </div>
                <div class="sign_left">
                    <h2 class="txt_title02">허가부서</h2>
                    <div style="height: 185px;" class="table_type3 scroll">
                        <table cellpadding="0" cellspacing="0" caption="허가부서" border="0" class="permitListTable">
                            <caption>허가부서</caption>
                            <colgroup>
                                <col width="10%" />
                                <col width="90%" />
                            </colgroup>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                    <p class="fl">
                        <span class="button bt2"><button type="button" class="permitAllCancle" style="${this.options.enablePermit !== true ? 'display: none;' : ''}">모두취소</button></span>
                    </p>
                </div>
            </div>
        </div>`;
    return template;
  }

  bindDefaultEvents() {
    const _this = this;
    $(".setRequestApprLine", this._targetId).on("click", function () {
      _this.findApproval();
    })
    $(".apprAllCancle", this._targetId).on("click", function () {
      _this.clearRequest();
    })
    $(".permitAllCancle", this._targetId).on("click", function () {
      _this.clearPermit();
    })
  }

  findApproval() {
    const _this = this;
    $.esutils.openPopup({
      url: "/popup/findapproval"
      , width: "690"
      , height: "640"
      , param: {menuId: _this.param.menuId}
      , fnCallback: function (data) {
        if (data) {
          _this.clearRequest();
          _this.addRequest(data.map(d => ({...d, apprDeptGbn: "1"})));
        }
      }
    });
  }

  renderApprLineByApi(apprDeptGbn, customData = {}) {
    const _this = this;
    $.esutils.getApi(apprDeptGbn === "1" ? "/api/common/approval/request" : "/api/common/approval/permit", {
      ...this.param,
      apprDeptGbn
    }, function (result) {
      let renderData = result.data;
      if(renderData && renderData.length > 0) {
        renderData = [...(customData.before || []), ...renderData, ...(customData.after || [])];
      }
      // permit 활성화가 아닌 경우 disabled 처리 및 자동 추가
      _this.renderApprLine(apprDeptGbn, renderData, apprDeptGbn === "2" && _this.options.enablePermit !== true);
    });
  }

  renderApprLine(apprDeptGbn, data, isFixed) {
    const _this = this;
    const renderTarget = apprDeptGbn === "1" ? ".requestListTable tbody" : ".permitListTable tbody";
    if (apprDeptGbn === "1") {
      _this.requestList = data;
    } else {
      _this.permitList = data;
    }
    $(renderTarget, _this._targetId).empty();

    if (apprDeptGbn === "1") {
      _this.clearRequest();
    } else if (apprDeptGbn === "2") {
      _this.clearPermit();
    }

    if (data && data.length > 0) {
      const html = data.map(d => {
        let apprInfoText = "";

        if (d.entrustYn === "Y") {
          apprInfoText = `${d.entrustDeptNm} ${d.entrustEmpNm} ${d.entrustJwNm || ''} ( 위임 : ${d.apprEmpNm} ${d.apprJwNm || ''} )`;
        } else {
          apprInfoText = `${d.apprDeptNm} ${d.apprEmpNm} ${d.apprJwNm || ''}`;
        }

        return `<tr class="result_line">
                        <td class="center result_chk"><input type="checkbox" name="requestCheck" apprDeptGbn="${apprDeptGbn}" apprEmpId="${d.apprEmpId}" class="border_none" ${isFixed ? 'disabled="disabled"' : ''}/></td>
                        <td class="result_td">${apprInfoText}</td>
                    </tr>`
      }).join("");
      $(renderTarget, _this._targetId).html(html);

      $(renderTarget, _this._targetId).find("[name=requestCheck]").on("change", function () {
        const checked = $(this).prop("checked");

        const apprDeptGbn = $(this).attr("apprDeptGbn");
        const apprEmpId = $(this).attr("apprEmpId");
        const empInfo = {
          apprDeptGbn,
          ...apprDeptGbn === "1" ? _this.requestList.find(d => d.apprEmpId = apprEmpId) : _this.permitList.find(d => d.apprEmpId = apprEmpId)
        }

        if (checked) {
          if (apprDeptGbn === "1") {
            _this.addRequest([empInfo]);
          } else {
            _this.addPermit([empInfo]);
          }
        } else {
          if (apprDeptGbn === "1") {
            _this.removeRequest(empInfo.apprEmpId);
          } else {
            _this.removePermit(empInfo.apprEmpId);
          }
        }
      });

      // permit 활성화가 아닌 경우 자동 추가
      if (apprDeptGbn === "2" && _this.options.enablePermit !== true) {
        this.addPermit(data);
      }
    } else {
      $(renderTarget, _this._targetId).html(`<tr><td colspan='5' class='center' height='146px;'><p class='search_result'>해당 조회결과가 없습니다.</p></td></tr>`);
    }
  }

  renderSavedApprLine() {
    if (this.savedRequestList) {
      const html = this.savedRequestList.map(
          d => `<span style='width:150px' class='${d.apprResult === "1" ? "approve" : d.apprResult === "2" ? "rejected" : "signbefore"}'><span>${d.apprEmpNm} ${d.apprJwNm || ''}</span></span>`).join(
          "");
      $(".requestLineTd", this._targetId).html(html);

    }
    if (this.savedPermitList) {
      const html = this.savedPermitList.map(
          d => `<span style='width:150px' class='${d.apprResult === "1" ? "approve" : d.apprResult === "2" ? "rejected" : "signbefore"}'><span>${d.apprEmpNm} ${d.apprJwNm || ''}</span></span>`).join(
          "");
      $(".permitLineTd", this._targetId).html(html);
    }
  }

  clearRequest() {
    const disabledEmpIds = $(`[name=requestCheck][apprDeptGbn='1']:disabled`).toArray().map(d => $(d).attr("apprEmpId")) || [];

    this.savedRequestList = this.savedRequestList.filter(d => disabledEmpIds.includes(d.apprEmpId));
    this.renderSavedApprLine();
    $(`[name=requestCheck][apprDeptGbn='1']:not(:disabled)`).prop("checked", false);
  }

  addRequest(empInfoList) {
    const empIdList = this.savedRequestList.map(d => d.apprEmpId);
    this.savedRequestList = this.savedRequestList.concat(empInfoList.filter(d => !empIdList.includes(d.apprEmpId)));
    this.renderSavedApprLine();
    this.savedRequestList.map(d => {
      $(`[name=requestCheck][apprDeptGbn="1"][apprEmpId=${d.apprEmpId}]`).prop("checked", true);
    });
  }

  removeRequest(apprEmpId) {
    this.savedRequestList = this.savedRequestList.filter(d => d.apprEmpId !== apprEmpId);
    this.renderSavedApprLine();
    $(`[name=requestCheck][apprDeptGbn="1"][apprEmpId=${apprEmpId}]`).prop("checked", false);
  }

  clearPermit() {
    const disabledEmpIds = $(`[name=requestCheck][apprDeptGbn='2']:disabled`).toArray().map(d => $(d).attr("apprEmpId")) || [];

    this.savedPermitList = this.savedPermitList.filter(d => disabledEmpIds.includes(d.apprEmpId));
    this.renderSavedApprLine();
    $(`[name=requestCheck][apprDeptGbn='2']:not(:disabled)`).prop("checked", false);
  }

  addPermit(empInfoList) {
    const empIdList = this.savedPermitList.map(d => d.apprEmpId);
    this.savedPermitList = this.savedPermitList.concat(empInfoList.filter(d => !empIdList.includes(d.apprEmpId)));
    this.renderSavedApprLine();
    this.savedPermitList.map(d => {
      $(`[name=requestCheck][apprDeptGbn="2"][apprEmpId=${d.apprEmpId}]`).prop("checked", true);
    });
  }

  removePermit(apprEmpId) {
    this.savedPermitList = this.savedPermitList.filter(d => d.apprEmpId !== apprEmpId);
    this.renderSavedApprLine();
    $(`[name=requestCheck][apprDeptGbn="2"][apprEmpId=${apprEmpId}]`).prop("checked", false);
  }

  getApproval() {
    return {
      approval: {
        savedRequestApproverLine: this.convertApprovalData(this.savedRequestList),
        savedPermitApproverLine: this.convertApprovalData(this.savedPermitList),
        canceletc: $("[name=canceletc]", this._targetId).val(),
        schemaNm: this.options.schemaNm || "",
        applEmpId: this.param.empId,
        applEmpNm: this.param.empNm,
        applDeptId: this.param.deptId,
        applDeptNm: this.param.deptNm,
        applJwNm: this.param.jwNm,
        acIp: this.param.acIp,
        crtBy: this.param.empId,
        delYn: "N",
      }
    }
  }

  convertApprovalData(list) {
    return list.map(d => ({
      ...d,
      deptId: d.apprDeptId,
      empId: d.apprEmpId,
      jwId: d.apprJwId,
    }))
  }

  generateTemplateView() {
    return `
    <h1 class="txt_title01 fl">결재절차</h1>
    <div class="sign_descript">
      <ul>
        <li class="sign01">승인</li>
        <li class="sign02">부결</li>
        <li class="sign03">준비</li>
      </ul>
    </div>
    <table cellpadding="0" cellspacing="0" border="0" class="view_board appr_save_line_table" style="table-layout: fixed;">
      <colgroup>
      <col width="151" />
      <col width="699" />
      </colgroup>
      <tbody>
        <tr>
          <th class="requestLineTh">요청부서</th>
          <td class="requestLineTd" valign="middle"></td>
        </tr>
        <tr>
          <th class="permitLineTh">허가부서</th>
          <td class="permitLineTd" valign="middle"></td>
        </tr>
        <tr class="canceletcTr">
          <th>승인/부결 사유</th>
          <td class="canceletcTd">
            <div class="approve_board">
              <ul class="approve_list canceletc_div" style="display:none;"></ul>
            </div>
          </td>
        </tr>
      </tbody>
    </table>`;
  }

  renderView() {
    const _this = this;
    $.esutils.getApi(`/api/common/approval/saved/${_this.param.docId}`, null, function (result) {
      if (result.data) {
        const {docView, saveList} = result.data;

        if (docView.applDeptNm) {
          $(".canceletc_div", _this._targetId).append(
              `<li>
                <div class='list_wrap'>
                  <p class='list_no'><span class='signbefore'><span>상신</span></span></p>
                  <div style='width:620px' class='list_con'>
                    <div class='list_btm'>
                      <p class='list_reg_info'>작성자 : ${docView.applDeptNm || ''} ${docView.applEmpNm}ㅣ 작성일 : ${docView.modDtm}</p>
                    </div>
                    <p>${docView.canceletc || ''}</p>
                  </div>
                </div>
              </li>`).show();
        }

        $(".requestLineTd", _this._targetId).html(saveList.filter(d => d.apprDeptGbn === "1").map(d => _this.renderSavedLine(d)).join(""));
        $(".permitLineTd", _this._targetId).html(saveList.filter(d => d.apprDeptGbn === "2").map(d => _this.renderSavedLine(d)).join(""));

        $(".canceletc_div", _this._targetId).append(saveList.map(d => {
          if (!d.modDtm) {
            return '';
          }
          const apprTextInfoByResult = _this.apprTextInfo[d.apprResult || "before"];
          const entrustArea = d.retrEmpId ? `<p class='list_reg_info'>위임자 : ${d.retrDeptNm} ${d.entrustEmpNm} ${d.entrustJwNm || ''}</p>` : '';
          return `
          <li>
            <div class='list_wrap'>
              <p class='list_no'><span class='${apprTextInfoByResult.classNm}'><span>${apprTextInfoByResult.resultNm}</span></span></p>
              <div style='width:620px' class='list_con'>
                <div class='list_btm'>
                  <p class='list_reg_info'>결재자 : ${d.deptNm || ''} ${d.empNm} ㅣ 결재일 : ${d.apprDtm}</p>
                  ${entrustArea}
                </div>
                <p>${d.canceletc ? d.canceletc.replaceAll("\n", "<br/>") : ""}</p>
              </div>
            </div>
          </li>
          `;
        }).join(""));
      }
    });
  }

  renderSavedLine(data) {
    const apprTextInfoByResult = this.apprTextInfo[data.apprResult || "before"];
    let entrustArea = data[apprTextInfoByResult.entrustIdCol]
        ? `<span class='${apprTextInfoByResult.classNm}' style='width:230px'><span>[위임]${data.entrusEmpNm} ${data.entrusJwNm}(${data.entrusEmpId})</span>`
        : '';
    return `<span class='${apprTextInfoByResult.classNm}' style='width:230px'><span>${data.empNm} ${data.jwNm}(${data.empId})</span></span>${entrustArea}`;
  }

}
