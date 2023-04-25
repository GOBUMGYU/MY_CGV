$(document).ready(function (){
    /**
     * 게시판 등록폼 유효성 체크
     */
    $("#btnBoardWrite").click(() => {
       if($("#btitle").val() == "") {
           alert("제목을 입력해주세요");
           $("#btitle").focus();
           return false;
       } else {
           //서버전송
           boardWriteForm.submit();
       }
    });
});