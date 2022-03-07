let dt = null;
// イベントを登録
$(document).on('dblclick', (e) => {
	e.preventDefault();
});
$(document).on('click', '#aLogout',() => {
	$('#logoutForm').submit();
});
$(document).on('click', '#last-year', () => {
	dt = new Date($("#cal-date").val());
	dt.setFullYear(dt.getFullYear() - 1);
	displayCalendarData(dt);
});
$(document).on('click', '#last-mon', () => {
	dt = new Date($("#cal-date").val());
	dt.setMonth(dt.getMonth() - 1);
	displayCalendarData(dt);
});
$(document).on('click', '#next-mon', () => {
	dt = new Date($("#cal-date").val());
	dt.setMonth(dt.getMonth() + 1);
	displayCalendarData(dt);
});
$(document).on('click', '#next-year', () => {
	dt = new Date($("#cal-date").val());
	dt.setFullYear(dt.getFullYear() + 1);
	displayCalendarData(dt);
});
$(document).on('click', '#today', () => {
	const today = new Date();
	displayCalendarData(today);
	hideSearchArea();
	hideTimeline();
	loadDiary(today);
});
$(document).on('click', '#searchBtn1', () => {
	const keyword = $('#search1').val();
	$('#search1').val("");
	$('#search2').val(keyword);
	displaySearchMode();
	searchDiaryAjax(keyword);
});
$(document).on('click', '#searchBtn2', () => {
	const keyword = $('#search2').val();
	searchDiaryAjax(keyword);
	displaySearchMode();
});
$(document).on('keyup', '.input', () => {
	//const num = this.id.slice(-1);
	//const count = $(this).find("textarea").val().length;
	$("#count1").text($('#good1').val().length);
	$("#count2").text($('#good2').val().length);
	$("#count3").text($('#good3').val().length);
});
$('.showTimeline').on('click', () => {
	hideSlideInMenu();
	$(".diary").hide();
	showTimeline();
});

// 関数を宣言
const displayCalendarData = (date) => {
	loadCalendarData(formatDateSlash(date));
}
const clickCalendarDay = date => {
	hideSearchArea();
	hideTimeline();
	loadDiary(date);
}

const loadCalendarData = (date) => {
	$.ajax({
		url: "/loadCalendar",
		type: "GET",
		//dataType: "json",
		dataType: "html",
		data: {
			targetDate: date,
			_csrf: $("*[name=_csrf]").val()
		}
	}).done((data) => {
		$(".out-cal").html(data);
		date = new Date(date);
		$(".cal-title > p").text(formatMonthForDate(date));
		$("#cal-date").val(formatDateSlash(date));
		//displayCalendar(data);
	}).fail(() => {
		alert("loadCalendar error!");
	});
}

const showTimeline = () => {
	//$(".timeline").show();
	loadTimeline();
	hideSearchArea();
}

const loadTimeline = () => {
	$.ajax({
		url: "/loadTimeline",
		type: "GET",
		dataType: "html",
		data: {
			_csrf: $("*[name=_csrf]").val()
		}
	}).done((data) => {
		displayTimeline(data);
	}).fail(() => {
		alert("loadTimeline error!");
	});
}

const displayTimeline = (data) => {
	$('.timeline-out').html(data);
}

const hideTimeline = () => {
	$('.timeline').hide();
}

const displaySearchMode = () => {
	showSearchArea();
	$(".diary").hide();
	hideTimeline();
}

const searchDiaryAjax = (keyword) => {
	$.ajax({
		url: "/searchDiary",
		type: "GET",
		dataType: "html",
		data: {
			keyword: keyword,
			_csrf: $("*[name=_csrf]").val()
		}
	}).done((data) => {
		displayTimeline(data);
	}).fail(() => {
		alert("search error!");
	});
}
