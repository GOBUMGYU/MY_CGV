$(document).ready(function (){

    initAjax(1);

    function initAjax(rpage) {

        $.ajax({
           url:"notice/list?rpage="+rpage,
           success:function(result){
               let dataset = JSON.parse(result);

               var output = "<table class='board'>";
               output += "<tr>"
               output += "<th>번호</th>"
               output += "<th>제목</th>"
               output += "<th>등록날짜</th>"
               output += "<th>조회수</th>"
               output += "</tr>"
               for(obj of dataset.list) {
                   output += "<tr>"
                   output += "<td>"+obj.nid+"</td>"
                   output += "<td><a href='notice/content' class='bclass' id='"+obj.nid+"'>"+obj.ntitle+"</a></td>"
                   output += "<td>"+obj.createAt+"</td>"
                   output += "<td>"+obj.nhits+"</td>"
                   output += "</tr>"
               }
               output += "<tr>"
               output += "<td colspan='4'><div id='ampaginationsm'></div></td>"
               output += "</tr>"
               output += "</table>";

               $("table.board").remove();
               $("h1").after(output);

               noticePager(dataset.dbCount, dataset.pageSize, dataset.rpage);

               //페이징 번호 클릭 시 이벤트 처리
               jQuery('#ampaginationsm').on('am.pagination.change',function(e){
                   //alert(e.page);
                   jQuery('.showlabelsm').text('The selected page no: '+e.page);
                   //$(location).attr('href', "http://localhost:9000/mycgv/notice/notice_list.jsp?rpage="+e.page);
                   //함수를 호출하는 방식으로 수정
                   initAjax(e.page);
               });

               //제목에 대한 이벤트 처리
               $(".bclass").click(function(){
                   //alert("제목클릭;; nid=" + $(this).attr("id"));
                   //함수 호출을 통해 기능 구현
                   noticeContent($(this).attr("id"));
               });

           } //success
        }); //ajax
    } //initAjax

    function noticePager(dbCount,pageSize,rpage){
        //alert(dbCount+","+pageSize+","+rpage);
        var pager = jQuery('#ampaginationsm').pagination({

            maxSize: 7,	    		// max page size
            totals: dbCount,	// total rows
            page: 	rpage,		// initial page
            pageSize: pageSize,	// max number items per page

            // custom labels
            lastText: '&raquo;&raquo;',
            firstText: '&laquo;&laquo;',
            prevText: '&laquo;',
            nextText: '&raquo;',

            btnSize:'sm'	// 'sm'  or 'lg'
        });
    }

    //noticeContent 열기 = 제목 클릭 이벤트 - bclass에 대한 기능 구현
    function noticeContent(nid) {
        $.ajax({
           url:"notice/content?nid="+nid,
           success:function(result) {
               let data = JSON.parse(result);

               let output = "<table class='boardContent'>";
               output += "<tr><th>등록일자</th>";
               output += "<td>"+ data.ndate +"</td>";
               output += "<th>조회수</th>";
               output += "<td>"+ data.nhits +"</td></tr>";
               output += "<tr>";
               output += "<th>제목</th>";
               output += "<td colspan='3'>" + data.ntitle + "</td>";
               output += "</tr>";
               output += "<tr>";
               output += "<th>내용</th>";
               output += "<td colspan='3'>" + data.ncontent + "<br><br><br></td>";
               output += "</tr>";
               output += "<tr>";
               output += "<td colspan='4'>";
               output += "<button type='button' class='btn_style' id='backList'>리스트</button>";
               output += "<button type='button' class='btn_style' id='backHome'>홈으로</button>";
               output += "</td></tr>";
               output += "</table>";

               $("table.board").remove();
               $("h1").after(output);

               $("#backList").click(function(){
                   $(location).attr("href","/notice");
                   //location.href = "이동할 주소";
               });

               //홈으로 버튼에 대한 이벤트
               $("#backHome").click(function(){
                   $(location).attr("href","/index");
               });
           }
        });
    }

});