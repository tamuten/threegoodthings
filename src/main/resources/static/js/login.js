$(document).on('click', '#signup', (e) => {
	e.preventDefault();
	getSignupAjax();
});

const getSignupAjax = () => {
	$.ajax({
		url: "/signup",
		type: "GET",
		dataType: "html"
		//,
		/*data: {
			_csrf: $("*[name=_csrf]").val()
		}*/
	}).done((data) => {
		console.log(data);
		//$('.login-wrapper').html(data);
	}).fail(() => {
		alert("get signup error!");
	});
}