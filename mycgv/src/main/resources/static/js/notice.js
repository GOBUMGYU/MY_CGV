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
                   output += "<td><a href='#' class='bclass' id='"+obj.nid+"'>"+obj.ntitle+"</a></td>"
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


           } //success
        }); //ajax
    } //initAjax
});