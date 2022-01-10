var num = 0;
var val = null;
$(".diaryBtn").on("click", function(e) {
	hideSearchArea();
	showDiary();
	clearInputContent();
	hideEditArea();
});

$(".searchBtn").on("click", function(e) {
	showSearchArea();
	hideDiary();
	clearInputContent();
	hideEditArea();
});

// 編集モードに変更
$(".editBtn, .goodText").on("click", function(e) {
	num = this.id.slice(-1);
	$(".display").hide();
	$(".move").hide();
	$("#input" + num).show();
	var $textArea = $("#input" + num).find("textarea");
	$textArea.focus();
	val = $textArea.val();
	// 				$textArea.setSelectionRange(2, 5);
	$(".edit").show();
});

$("#cancel").on("click", function(e) {
	clearInputContent();
	hideEditArea();
	$(".display").show();
	$(".move").show();
});

function clearInputContent(){
	$("#input" + num).find("textarea").val(val);
	$("#count" + num).text(val.length);
}

function hideEditArea(){
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

function showSearchArea(){
	$(".searchMode").show();
}

function hideSearchArea(){
	$(".searchMode").hide();
}

$("#save").on("click", function(e) {
	var good = $('#good' + num).val();

	if (good.length > 400) {
		$(".error").find("p").text('400文字以下で入力してください。');
		$(".error").show();
		return;
	}
	e.preventDefault();
	$.ajax({
		url: "/register",
		type: "POST",
		dataType: "json",
		data: {
			good: good,
			date: $("#date").val(),
			num: num,
			_csrf: $("*[name=_csrf]").val()
		}
	}).done(function(data) {
		alert("登録が完了しました");
		hideEditArea();
		loadDiary($("#date").val());
		showDiary();
	}).fail(function() {
		alert("error!");
	});
});

$(".yesterday").on("click", function(e) {
	e.preventDefault();
	var date = new Date($('#date').val());
	var targetDate = setDateYesterday(date);
	loadDiary(formatDateSlash(targetDate));
})

$(".tomorrow").on("click", function(e) {
	e.preventDefault();
	var date = new Date($('#date').val());
	var targetDate = setDateTomorrow(date);
	loadDiary(formatDateSlash(targetDate));
})

function loadDiary(targetDate) {
	$.ajax({
		url: "/loadDiary",
		type: "GET",
		dataType: "json",
		data: {
			date: targetDate,
			_csrf: $("*[name=_csrf]").val()
		}
	}).done(function(data) {
		displayData(data);
	}).fail(function() {
		alert("error!");
	});
}

function displayData(data) {
	var displayDate = new Date(data.date);
	$(".date").find('p').text(formatDateKanjiForDate(displayDate));

	// 日付更新前の記載内容をリセット
	for (var i = 1; i <= 3; i++) {
		$("#goodText" + i).text("");
		$("#good" + i).val(null);
		$("#count" + i).text(0);
	}

	if (data.good1 != null) {
		$("#goodText1").text(data.good1);
		$("#goodText1").show();
		$("#edit1").hide();
		$("#good1").val(data.good1);
		$("#count1").text(data.good1.length);
	} else {
		$("#goodText1").hide();
		$("#edit1").show();
	}
	if (data.good2 != null) {
		$("#goodText2").text(data.good2);
		$("#goodText2").show();
		$("#edit2").hide();
		$("#good2").val(data.good2);
		$("#count2").text(data.good2.length);
	} else {
		$("#goodText2").hide();
		$("#edit2").show();
	}
	if (data.good3 != null) {
		$("#goodText3").text(data.good3);
		$("#goodText3").show();
		$("#edit3").hide();
		$("#good3").val(data.good3);
		$("#count3").text(data.good3.length);
	} else {
		$("#goodText3").hide();
		$("#edit3").show();
	}
	$("#date").val(formatDateSlash(displayDate));
}

$('.calendarDay').on('click', function() {
	var calDate = new Date($('#cal-date').val());
	calDate.setDate($(this).text());
	loadDiary(formatDateSlash(calDate));
});