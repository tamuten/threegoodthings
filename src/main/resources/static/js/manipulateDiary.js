let inum = 0;
$(".diaryBtn").on("click", function() {
	hideSearchArea();
	loadDiary(new Date());
	hideTimeline();
});

$(".searchBtn").on("click", function() {
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
	$(".save").css('display', 'flex');
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
		alert("登録が完了しました");
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

$(document).on('click', '.calendarDay', function() {
	var calDate = new Date($('#cal-date').val());
	calDate.setDate($(this).text());
	loadDiary(formatDateSlash(calDate));
});

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
		//dataType: "json",
		dataType: "html",
		data: {
			date: targetDate,
			_csrf: $("*[name=_csrf]").val()
		}
	}).done(function(data) {
		//displayData(data);
		displayDiary(data);
		displayCalendarData(new Date(targetDate));
	}).fail(function() {
		alert(" loadDiary error!");
	});
}

function displayDiary(data) {
	$(".diary-out").html(data);
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
