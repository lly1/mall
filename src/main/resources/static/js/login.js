$(function(){
	$('#switch_qlogin').click(function(){
		$('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
		$('#switch_qlogin').removeClass("switch_btn").addClass('switch_btn_focus');
		$('#switch_bottom').animate({left:'0px',width:'70px'});
		$('#qlogin').css('display','none');
		$('#web_qr_login').css('display','block');
		
		});
	$('#switch_login').click(function(){
		
		$('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
		$('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
		$('#switch_bottom').animate({left:'154px',width:'70px'});
		
		$('#qlogin').css('display','block');
		$('#web_qr_login').css('display','none');
		});
    if(getParam("a")=='0')
    {
        $('#switch_login').trigger('click');
    }

});



//根据参数名获得该参数 pname等于想要的参数名
function getParam(pname) { 
    var params = location.search.substr(1); // 获取参数 平且去掉？ 
    var ArrParam = params.split('&'); 
    if (ArrParam.length == 1) { 
        //只有一个参数的情况 
        return params.split('=')[1]; 
    } 
    else { 
         //多个参数参数的情况 
        for (var i = 0; i < ArrParam.length; i++) { 
            if (ArrParam[i].split('=')[0] == pname) { 
                return ArrParam[i].split('=')[1]; 
            } 
        } 
    } 
}


var reMethod = "GET",
	pwdmin = 2;

$(document).ready(function() {
    if ($.cookie("rmbUser") == "true") {
        $("#ck_remMe").attr("checked", true);
        $("#code").val($.cookie("code"));
        $("#password").val($.cookie("password"));
    }
    $('#login').click(function() {
        if ($('#u').val() == "") {
            $('#u').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#loginCue').html("<font color='red'><b>×用户名不能为空</b></font>");
            return false;
        }

        if ($('#u').val().length < 2 || $('#u').val().length > 16) {

            $('#u').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#loginCue').html("<font color='red'><b>×用户名位2-16字符</b></font>");
            return false;
        }
        if ($('#p').val() == "") {
            $('#p').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#loginCue').html("<font color='red'><b>×密码不能为空</b></font>");
            return false;
        }
        $('#loginCue').html("<font color='red'><b>登录中···</b></font>");
        if ($("#ck_remMe").is(':checked')) {
            var str_username = $("#u").val()
            var str_password = $("#p").val();
            $.cookie("rmbUser", "true", { expires: 7 }); //存储一个带7天期限的cookie
            $.cookie("code", str_username, { expires: 7 });
            $.cookie("password", str_password, { expires: 7 });
        }
        else {
            $.cookie("rmbUser", "false", { expire: -1 });
            $.cookie("code", "", { expires: -1 });
            $.cookie("password", "", { expires: -1 });
        }

    });
	$('#reg').click(function() {
		if ($('#user').val() == "") {
			$('#user').focus().css({
				border: "1px solid red",
				boxShadow: "0 0 2px red"
			});
			$('#userCue').html("<font color='red'><b>×用户名不能为空</b></font>");
			return false;
		}

		if ($('#user').val().length < 2 || $('#user').val().length > 16) {

			$('#user').focus().css({
				border: "1px solid red",
				boxShadow: "0 0 2px red"
			});
			$('#userCue').html("<font color='red'><b>×用户名位2-16字符</b></font>");
			return false;

		}

		if ($('#passwd').val().length < pwdmin) {
			$('#passwd').focus();
			$('#userCue').html("<font color='red'><b>×密码不能小于" + pwdmin + "位</b></font>");
			return false;
		}

		var sqq = /^[1-9]{1}[0-9]{4,9}$/;
		if (!sqq.test($('#qq').val()) || $('#qq').val().length < 5 || $('#qq').val().length > 12) {
			$('#qq').focus().css({
				border: "1px solid red",
				boxShadow: "0 0 2px red"
			});
			$('#userCue').html("<font color='red'><b>×QQ号码格式不正确</b></font>");return false;
		} else {
			$('#qq').css({
				border: "1px solid #D7D7D7",
				boxShadow: "none"
			});
		}
		$('#regUser').submit();
	});
    $('#regP').click(function() {

        if ($('#picture').val() == "") {
            $('#user').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×手机图片不能为空</b></font>");
            return false;
        }

        if ($('#phonename').val() == "") {
            $('#user').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×手机名不能为空</b></font>");
            return false;
        }

        if ($('#phonename').val().length < 2 || $('#phonename').val().length > 16) {

            $('#user').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×手机名位2-16字符</b></font>");
            return false;

        }
        if ($('#price').val() == "") {
            $('#user').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×价格不能为空</b></font>");
            return false;
        }
        if ($('#catalogname').val() == "") {
            $('#user').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×类别不能为空</b></font>");
            return false;
        }
        $('#regPhone').submit();
    });
    $('#regC').click(function() {
        if ($('#catalogname').val() == "") {
            $('#user').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×类别不能为空</b></font>");
            return false;
        }
        $('#regCat').submit();
    });


});