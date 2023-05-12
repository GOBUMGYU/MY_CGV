$(document).ready(function () {
    initNoticeList(1);
    movieList();

    function initNoticeList(rpage) {

        $.ajax({
            url : "notice/list?rpage="+rpage,
            dataType: "json",
            success : function(result) {
                console.log(result);
                let notice = result;
                let output = "<div class='notice_style'><h3>공지사항</h3>";
                output += "<div>&nbsp;&nbsp;<span id='pre'>pre</span>&nbsp;&nbsp;|";
                output += "&nbsp;&nbsp;<span id='next'>next</span>&nbsp;&nbsp;|";
                output += "&nbsp;&nbsp;<span id='more'>more</span></div>";
                output += "<ul class='notice_list'>";
                for(data of notice.list){
                    output += "<li>";
                    output += "<span>"+ data.nid +"</span>";
                    output += "<span>"+ data.ntitle +"</span>";
                    output += "<span>"+ data.createAt +"</span>";
                    output += "<span>"+ data.nhits +"</span>";
                    output += "</li>";
                }

                output += "</ul></div>";


                //3. 생성된 Dynamic HTML 코드를 해당위치에 출력
                $("div.notice_style").remove();
                $("div.s2_article > div").after(output);

                //pre, next 버튼 활성화
                if(rpage > 1 && rpage < notice.pageCount ){
                    $("#pre").css("visibility","visible").css("color","black");
                    $("#next").css("visibility","visible").css("color","red");
                } else if(rpage === 1){
                    $("#pre").css("visibility","hidden");
                    $("#next").css("visibility","visible").css("color","red");
                } else {
                    $("#pre").css("visibility","visible").css("color","red");
                    $("#next").css("visibility","hidden");
                }

                //4. Dynamic HTML 코드에서 이벤트 처리 진행
                $("#pre").click(function(){
                    initNoticeList(rpage-1);
                });

                $("#next").click(function(){
                    initNoticeList(rpage+1);
                });

                $("#more").click(function(){
                    $(location).attr("href","notice");
                });

            }//success
        });//ajax
    }//initNoticeList
});