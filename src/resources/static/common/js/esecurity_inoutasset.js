class EsecurityInoutasset {
  constructor(inoutApplNo) {
    this.inoutApplNo = inoutApplNo;
    this.data = null;
    this.initData = (async () => {
      await this.fetchData();
    })();
  }

  async callback(fnCallback) {
    await this.initData;
    if (fnCallback) {
      fnCallback(this.data);
    }
  }

  async fetchData() {
    const result = await $.esutils.getApi(`/api/inoutasset/inoutcommon/view/${this.inoutApplNo}`);

    if (result.data) {
      let viewInfo = {...result.data.viewinfo, ...result.data.processInfo};
      const articleList = result.data.articleList;

      const mycompanyko = viewInfo.mycompanyno;
      let mycompanykonm = viewInfo.mycompanyno;
      if (mycompanyko === "1101000001") {
        mycompanykonm = "이천";
      } else if (mycompanyko === "1102000001") {
        mycompanykonm = "청주1";
      } else if (mycompanyko === "1105000001") {
        mycompanykonm = "청주2";
      } else if (mycompanyko === "1106000001") {
        mycompanykonm = "청주3";
      } else if (mycompanyko === "1103000001") {
        mycompanykonm = "영동";
      } else if (mycompanyko === "1107000001") {
        mycompanykonm = "분당사무소(서현)";
      } else if (mycompanyko === "1108000001") {
        mycompanykonm = "분당사무소(정자)";
      } else if (mycompanyko === "1130000001") {
        mycompanykonm = "원자재통합자재창고1";
      } else if (mycompanyko === "1131000001") {
        mycompanykonm = "청주완제품창고";
      } else if (mycompanyko === "1109000001") {
        mycompanykonm = "청주4";
      } else if (mycompanyko === "1110000001") {
        mycompanykonm = "원자재통합자재창고2";
      }
      viewInfo.outcompanykndNm = viewInfo.outcompanyknd === "1" ? `자사사업장 [ ${mycompanykonm} ${viewInfo.outcompanydeptNm} ]` : viewInfo.outcompanykndNm;

      let companynonm = "";
      const companyno = viewInfo.companyno;
      if (companyno === "1101000001") {
        companynonm = "이천";
      } else if (companyno === "1102000001") {
        companynonm = "청주1";
      } else if (companyno === "1105000001") {
        companynonm = "청주2";
      } else if (companyno === "1106000001") {
        companynonm = "청주3";
      } else if (companyno === "1103000001") {
        companynonm = "영동";
      } else if (companyno === "1107000001") {
        companynonm = "분당사무소(서현)";
      } else if (companyno === "1108000001") {
        companynonm = "분당사무소(정자)";
      } else if (companyno === "1130000001") {
        companynonm = "원자재통합자재창고1";
      } else if (companyno === "1131000001") {
        companynonm = "청주완제품창고";
      } else if (companyno === "1109000001") {
        companynonm = "청주4";
      } else if (companyno === "1110000001") {
        companynonm = "원자재통합자재창고2";
      }
      viewInfo.companynoNm = companynonm;

      let returncompanyareakndnm = "";
      const returncompanyareaknd = viewInfo.returncompanyareaknd;
      if (returncompanyareaknd === "101") {
        returncompanyareakndnm = "이천";
      } else if (returncompanyareaknd === "102") {
        returncompanyareakndnm = "청주1";
      } else if (returncompanyareaknd === "105") {
        returncompanyareakndnm = "청주2";
      } else if (returncompanyareaknd === "106") {
        returncompanyareakndnm = "청주3";
      } else if (returncompanyareaknd === "103") {
        returncompanyareakndnm = "영동";
      } else if (returncompanyareaknd === "107") {
        returncompanyareakndnm = "분당사무소(서현)";
      } else if (returncompanyareaknd === "108") {
        returncompanyareakndnm = "분당사무소(정자)";
      } else if (returncompanyareaknd === "130") {
        returncompanyareakndnm = "원자재통합자재창고1";
      } else if (returncompanyareaknd === "131") {
        returncompanyareakndnm = "청주완제품창고";
      } else if (returncompanyareaknd === "109") {
        returncompanyareakndnm = "청주4";
      } else if (returncompanyareaknd === "110") {
        returncompanyareakndnm = "원자재통합자재창고2";
      }
      viewInfo.returncompanyareakndNm = returncompanyareakndnm;

      if (viewInfo.inoutetc) {
        viewInfo.inoutetc = viewInfo.inoutetc.replaceAll("\n", "<br/>");
      }

      this.data = {
        viewInfo,
        articleList
      }
    } else {
      this.data = {};
    }

    this.data = result.data;

    // default column names
    this.defaultColNames = {
      compNm: "회사명",
      inoutserialno: "반출입번호",
      empNm: "작성자",
      articlekndnoNm: "구분",
      articlegroupnm: "그룹",
      inoutkndname: "반출구분",
      writedate: "작성일",
      companynoNm: "반출사업장",
      returncompanyareakndNm: "최종반입사업장",
      sendYn: "파견/주재원",
      indate: "반입예정일자",
      outdate: "반출일자",
      realindate: "반입일자",
      outcompanykndNm: "상대처",
      prno: "PRNo, 문서번호",
      outreasonidNm: "반출사유",
      outreasonsubkndNm: "반출상세사유",
      inoutetc: "기타상세 반출사유",
    }
    ;
  }

  async inoutwrite(targetId, colDefs) {
    if (targetId && colDefs) {
      await this.initData;
      this.colDefs = colDefs;
      $(`#${targetId}`).html(this.generateInoutwrite());
    }
  }

  generateInoutwrite() {
    let tbody = "";
    if (this.colDefs && this.data.viewInfo) {
      const data = this.data.viewInfo;
      tbody = this.colDefs.map(d => {
        let contents;
        if (d.all) {
          contents = `<th>${this.renderTitle(d.all)}</th><td colspan="3">${this.renderData(data, d.all)}</td>`;
        } else {
          contents = `
            <th>${this.renderTitle(d.left)}</th>
            <td>${this.renderData(data, d.left)}</td>
            <th>${this.renderTitle(d.right)}</th>
            <td>${this.renderData(data, d.right)}</td>
          `;
        }
        return `<tr>${contents}</tr>`;
      }).join("");
    }
    return `
      <h1 class="txt_title01">반출입작성</h1>
      <table cellpadding="0" cellspacing="0" border="0" class="view_board">
        <colgroup>
        <col width="14%" />
        <col width="36%" />
        <col width="14%" />
        <col width="36%" />
        </colgroup>
        <tbody>
        ${tbody}
        </tbody>
      </table>`;
  }

  renderTitle(name = "") {
    let title;
    if (name.indexOf(";") > 0) {
      title = name.split(";")[1];
    } else {
      title = this.defaultColNames[name];
    }
    return title || "";
  }

  renderData(data = {}, name = "") {
    let text;
    if (name.indexOf(";") > 0) {
      text = data[name.split(";")[0]];
    } else {
      text = data[name];
    }
    return text || "";
  }

  async article(targetId) {
    if (targetId) {
      await this.initData;

      $(`#${targetId}`).html(this.generateArticle());

      this.gridUtil = new GridUtil({
        gridId: "articleGrid"
        , userData: this.data.articleList
        , isPaging: false
        , gridOptions: {
          width: 900,
          colData: this.getArticleGridColData(this.data),
        }
      });
      this.gridUtil.init();
    }
  }

  generateArticle() {
    return `<h1 class="txt_title01">반출입작성</h1><div id="articleGrid"></div>`
  }

  getArticleGridColData(data) {
    const {viewInfo} = data;
    let colData = [];
    if (viewInfo.articlekndno == "1") {
      // 물품 - 원자재, 부자재, 스페어파트, 설비자재
      // 원자재 : 1000000001
      // 부자재 : 1000000002
      // 스페어파트 : 1000000003
      // 설비자재 : 1000000182
      if (viewInfo.articlegroupid == "1000000001" || viewInfo.articlegroupid == "1000000002" || viewInfo.articlegroupid == "1000000003" || viewInfo.articlegroupid == "1000000182") {
        colData = [
          {
            headerName: "자재코드"
            , field: "serialno"
            , width: "15%"
          },
          {
            headerName: "이미지"
            , field: "imagepath"
            , width: "10%"
          },
          {
            headerName: "품명"
            , field: "articlename"
            , width: "20%"
          },
          {
            headerName: "Vendor p/n"
            , field: "venderpn"
            , width: "15%"
          },
          {
            headerName: "단위"
            , field: "unitname"
            , width: "10%"
          },
          {
            headerName: "규격"
            , field: "asize"
            , width: "10%"
          },
          {
            headerName: "수량"
            , field: "inoutcnt"
            , width: "10%"
          },
          {
            headerName: "첨부"
            , field: "filepath"
            , width: "10%"
          },
        ];
        //물품 - 자산
      } else if (viewInfo.articlegroupid == "1000000004") {
        colData = [
          {
            headerName: "자산번호"
            , field: "jsno"
            , width: "10%"
          },
          {
            headerName: "관리(OA)번호"
            , field: "mmno"
            , width: "10%"
          },
          {
            headerName: "제조번호(S/N)"
            , field: "serialno"
            , width: "10%"
          },
          {
            headerName: "이미지"
            , field: "imagepath"
            , width: "5%"
          },
          {
            headerName: "자산명"
            , field: "articlename"
            , width: "25%"
          },
          {
            headerName: "부서명"
            , field: "deptname"
            , width: "10%"
          },
          {
            headerName: "단위"
            , field: "unitname"
            , width: "10%"
          },
          {
            headerName: "수량"
            , field: "inoutcnt"
            , width: "10%"
          },
          {
            headerName: "첨부"
            , field: "filepath"
            , width: "10%"
          },
        ];
        //물품 - 원재료/저장품
      } else if (viewInfo.articlegroupid == "1000000142") {
        colData = [
          {
            headerName: "SAP Code"
            , field: "materialId"
            , width: "20%"
          },
          {
            headerName: "품명"
            , field: "articlename"
            , width: "50%"
          },
          {
            headerName: "단위"
            , field: "unitname"
            , width: "10%"
          },
          {
            headerName: "수량"
            , field: "inoutcnt"
            , width: "10%"
          },
          {
            headerName: "첨부"
            , field: "filepath"
            , width: "10%"
          },
        ];
        //물품 - 전기자재
      } else if (viewInfo.articlegroupid == "1000000156") {
        colData = [
          {
            headerName: "품명"
            , field: "articlename"
            , width: "55%"
          },
          {
            headerName: "Serial no."
            , field: "serialno"
            , width: "25%"
          },
          {
            headerName: "수량"
            , field: "inoutcnt"
            , width: "10%"
          },
          {
            headerName: "첨부"
            , field: "filepath"
            , width: "10%"
          },
        ];
        //물품 - 기타
      } else if (viewInfo.articlegroupid == "1000000013") {
        colData = [
          {
            headerName: "품명"
            , field: "articlename"
            , width: "50%"
          },
          {
            headerName: "이미지"
            , field: "imagepath"
            , width: "10%"
          },
          {
            headerName: "단위"
            , field: "unitname"
            , width: "10%"
          },
          {
            headerName: "규격"
            , field: "asize"
            , width: "10%"
          },
          {
            headerName: "수량"
            , field: "inoutcnt"
            , width: "10%"
          },
          {
            headerName: "첨부"
            , field: "filepath"
            , width: "10%"
          },
        ];
      } else {
        colData = [
          {
            headerName: "품명"
            , field: "articlename"
            , width: "50%"
          },
          {
            headerName: "이미지"
            , field: "imagepath"
            , width: "10%"
          },
          {
            headerName: "단위"
            , field: "unitname"
            , width: "10%"
          },
          {
            headerName: "규격"
            , field: "asize"
            , width: "10%"
          },
          {
            headerName: "수량"
            , field: "inoutcnt"
            , width: "10%"
          },
          {
            headerName: "첨부"
            , field: "filepath"
            , width: "10%"
          },
        ];
      }
      // 휴대용 전산장치 시작
    } else if (viewInfo.articlekndno == "2") {
      // 노트북
      if (ARTICLEGROUPID == "1000000010") {
        colData = [
          {
            headerName: "자산번호"
            , field: "jsno"
            , width: "10%"
          },
          {
            headerName: "관리(OA)번호"
            , field: "mmno"
            , width: "10%"
          },
          {
            headerName: "제조번호(S/N)"
            , field: "serialno"
            , width: "10%"
          },
          {
            headerName: "이미지"
            , field: "imagepath"
            , width: "10%"
          },
          {
            headerName: "물품명"
            , field: "articlename"
            , width: "20%"
          },
          {
            headerName: "부서명"
            , field: "deptname"
            , width: "10%"
          },
          {
            headerName: "단위"
            , field: "unitname"
            , width: "10%"
          },
          {
            headerName: "수량"
            , field: "inoutcnt"
            , width: "10%"
          },
          {
            headerName: "첨부"
            , field: "filepath"
            , width: "10%"
          },
        ];
      } else if (viewInfo.articlegroupid == "1000000056" || viewInfo.articlegroupid == "1000000051") {
        colData = [
          {
            headerName: "품명"
            , field: "articlename"
            , width: "50%"
          },
          {
            headerName: "용량"
            , field: "unitname"
            , width: "15%"
          },
          {
            headerName: "시리얼넘버"
            , field: "serialno"
            , width: "15%"
          },
          {
            headerName: "수량"
            , field: "inoutcnt"
            , width: "10%"
          },
          {
            headerName: "첨부"
            , field: "filepath"
            , width: "10%"
          },
        ];
      } else {
        colData = [
          {
            headerName: "사용승인번호"
            , field: "serialno"
            , width: "20%"
          },
          {
            headerName: "품명"
            , field: "articlename"
            , width: "40%"
          },
          {
            headerName: "용량"
            , field: "unitname"
            , width: "10%"
          },
          {
            headerName: "비고"
            , field: "asize"
            , width: "10%"
          },
          {
            headerName: "수량"
            , field: "inoutcnt"
            , width: "10%"
          },
          {
            headerName: "첨부"
            , field: "filepath"
            , width: "10%"
          },
        ];
      }
      // 문서
    } else if (viewInfo.ARTICLEKNDNO == "3") {
      colData = [
        {
          headerName: "문서번호"
          , field: "serialno"
          , width: "20%"
        },
        {
          headerName: "제목"
          , field: "unitname"
          , width: "60%"
        },
        {
          headerName: "페이지수"
          , field: "inoutcnt"
          , width: "10%"
        },
        {
          headerName: "첨부"
          , field: "filepath"
          , width: "10%"
        },
      ];
    } else if (viewInfo.articlekndno == "4") {
      colData = [
        {
          headerName: "자재코드"
          , field: "serialno"
          , width: "15%"
        },
        {
          headerName: "이미지"
          , field: "imagepath"
          , width: "10%"
        },
        {
          headerName: "품명"
          , field: "articlename"
          , width: "20%"
        },
        {
          headerName: "Vendor p/n"
          , field: "venderpn"
          , width: "15%"
        },
        {
          headerName: "단위"
          , field: "unitname"
          , width: "10%"
        },
        {
          headerName: "규격"
          , field: "asize"
          , width: "10%"
        },
        {
          headerName: "수량"
          , field: "inoutcnt"
          , width: "10%"
        },
        {
          headerName: "첨부"
          , field: "filepath"
          , width: "10%"
        },
      ];
    } else if (viewInfo.articlekndno == "5") {
      colData = [
        {
          headerName: "품명"
          , field: "articlename"
          , width: "50%"
        },
        {
          headerName: "이미지"
          , field: "imagepath"
          , width: "10%"
        },
        {
          headerName: "단위"
          , field: "unitname"
          , width: "10%"
        },
        {
          headerName: "규격"
          , field: "asize"
          , width: "10%"
        },
        {
          headerName: "수량"
          , field: "inoutcnt"
          , width: "10%"
        },
        {
          headerName: "첨부"
          , field: "filepath"
          , width: "10%"
        },
      ];
    } else if (viewInfo.articlekndno == "6") {
      if (viewInfo.articlegroupid == "1000000001") {
        colData = [
          {
            headerName: "자재코드"
            , field: "serialno"
            , width: "15%"
          },
          {
            headerName: "이미지"
            , field: "imagepath"
            , width: "10%"
          },
          {
            headerName: "품명"
            , field: "articlename"
            , width: "20%"
          },
          {
            headerName: "Vendor p/n"
            , field: "venderpn"
            , width: "15%"
          },
          {
            headerName: "단위"
            , field: "unitname"
            , width: "10%"
          },
          {
            headerName: "규격"
            , field: "asize"
            , width: "10%"
          },
          {
            headerName: "수량"
            , field: "inoutcnt"
            , width: "10%"
          },
          {
            headerName: "첨부"
            , field: "filename"
            , width: "10%"
          },
        ];
        //물품 - 자산
      } else {
        colData = [
          {
            headerName: "품명"
            , field: "articlename"
            , width: "35%"
          },
          {
            headerName: "이미지"
            , field: "imagepath"
            , width: "10%"
          },
          {
            headerName: "단위"
            , field: "unitname"
            , width: "10%"
          },
          {
            headerName: "규격"
            , field: "asize"
            , width: "10%"
          },
          {
            headerName: "수량"
            , field: "inoutcnt"
            , width: "10%"
          },
          {
            headerName: "첨부"
            , field: "filename"
            , width: "10%"
          },
          {
            headerName: "물품속성"
            , field: "attribute"
            , width: "15%"
          },
        ];
      }
    }
    return colData;
  }
}
