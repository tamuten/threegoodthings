let inum = 0;
$(".diaryBtn").on("click", function() {
	hideSlideInMenu();
	hideSearchArea();
	loadDiary(new Date());
	hideTimeline();
});

$(".searchBtn").on("click", function() {
	hideSlideInMenu();
	showSearchArea();
	hideDiary();
	hideEditArea();
	hideTimeline();
});

$(document).on("click", ".editBtn, .goodText", function() {
	inum = this.id.slice(-1);
	$(".display").hide();
	$(".move").hide();
	$("#input" + inum).show();
	//var $textArea = $("#input" + num).find("textarea");
	//$textArea.focus();
	//val = $textArea.val();
	// 				$textArea.setSelectionRange(2, 5);
	$(".edit").show();
	$(".save").show();
});

$(document).on("click", '#cancel', function() {
	const date = $('#date').val();
	loadDiary(date);
});

$(document).on("click", '#save', function(e) {
	e.preventDefault();
	const good = $('#good' + inum).val();

	if (good.length > 400) {
		$(".error").find("p").text('400文字以下で入力してください。');
		$(".error").show();
		return;
	}

	$.ajax({
		url: "/register",
		type: "POST",
		dataType: "json",
		data: {
			good: good,
			date: $("#date").val(),
			num: inum,
			_csrf: $("*[name=_csrf]").val()
		}
	}).done(function(data) {
		hideEditArea();
		loadDiary($("#date").val());
		showDiary();
	}).fail(function() {
		alert("save error!");
	});
});

$(document).on("click", '.yesterday', function() {
	var date = new Date($('#date').val());
	var targetDate = setDateYesterday(date);
	loadDiary(formatDateSlash(targetDate));
});

$(document).on("click", '.tomorrow', function() {
	var date = new Date($('#date').val());
	var targetDate = setDateTomorrow(date);
	loadDiary(formatDateSlash(targetDate));
});

function hideSlideInMenu(){
	$('#menu-btn-check').prop('checked', false);
}

function hideEditArea() {
	$(".input").hide();
	$(".error").hide();
	$(".edit").hide();
}

function showDiary() {
	$(".date-move").show();
	$(".display").show();
	$(".move").show();
}

function hideDiary() {
	$(".date-move").hide();
	$(".display").hide();
	$(".move").hide();
}

function showSearchArea() {
	$(".searchMode").show();
}

function hideSearchArea() {
	$(".searchMode").hide();
}

function loadDiary(targetDate) {
	$.ajax({
		url: "/loadDiary",
		type: "GET",
		dataType: "html",
		data: {
			date: targetDate,
			_csrf: $("*[name=_csrf]").val()
		}
	}).done(function(data, textStatus, jqXHR) {
		displayDiary(data);
		displayCalendarData(new Date(targetDate));
	}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
		if (XMLHttpRequest.status === 401) {
			location.href = '/login';
		}
		console.log(" loadDiary error!");
	});
}

function displayDiary(data) {
	$(".diary-out").html(data);
}