$(document).ready(function() {
    /**
     * 회원가입 폼 유효성 체크
     */
    $("#btnJoin").click(()=>{

        if($("#id").val() == ""){
            alert("아이디를 입력해주세요");
            $("#id").focus();
            return false;
        }else if($("#pass").val() == ""){
            alert("패스워드를 입력해주세요");
            $("#pass").focus();
            return false;
        }else if($("#cpass").val() == ""){
            alert("패스워드 확인을 입력해주세요");
            $("#cpass").focus();
            return false;
        }else if($("#name").val() == ""){
            alert("성명을 입력해주세요");
            $("#name").focus();
            return false;
        }else if($("input[name='gender']:checked").length == 0){
            alert("성별을 선택해주세요");
            return false;
        }else if($("#email1").val() == ""){
            alert("이메일을 입력해주세요");
            $("#email1").focus();
            return false;
        }else if($("#email2").val() == ""){
            alert("이메일 주소를 선택해주세요");
            $("#email3").focus();
            return false;
        }else if($("#addr1").val() == ""){
            alert("주소를 입력해주세요");
            $("#addr1").focus();
            return false;
        }else if($("#addr2").val() == ""){
            alert("상세주소를 입력해주세요");
            $("#addr2").focus();
            return false;
        }else if($("input[name='hp']:checked").length == 0){
            alert("통신사를 선택해주세요");
            return false;
        }else if($("#pnum1").val() == "default"){
            alert("폰번호를 선택해주세요");
            $("#pnum1").focus();
            return false;
        }else if($("#pnum2").val() == ""){
            alert("폰번호를 입력해주세요");
            $("#pnum2").focus();
            return false;
        }else if($("#pnum3").val() == ""){
            alert("마지막 폰번호를 입력해주세요");
            $("#pnum3").focus();
            return false;
        }else if($("input[name='hobby']:checked").length == 0){
            alert("취미를 선택해주세요");
            return false;
        }else{
            //서버전송
            joinForm.submit();
        }

    });
}); //ready Function