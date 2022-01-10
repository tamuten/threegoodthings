$(function() {
	var dt = null;
	$("#last-year").on("click", function() {
		dt = new Date($("#cal-date").val());
		dt.setFullYear(dt.getFullYear() - 1);
		displayCalendarData(dt);
	});

	$("#last-mon").on("click", function() {
		dt = new Date($("#cal-date").val());
		dt.setMonth(dt.getMonth() - 1);
		displayCalendarData(dt);
	});

	$("#next-mon").on("click", function() {
		dt = new Date($("#cal-date").val());
		dt.setMonth(dt.getMonth() + 1);
		displayCalendarData(dt);
	});

	$("#next-year").on("click", function() {
		dt = new Date($("#cal-date").val());
		dt.setFullYear(dt.getFullYear() + 1);
		displayCalendarData(dt);
	});

	$("#today").on("click", function() {
		var today = new Date();
		displayCalendarData(today);
		loadDiary(today)
	});

	var displayCalendarData = function(date) {
		$(".cal-title").find("p").text(formatMonthForDate(date));
		$("#cal-date").val(formatDateSlash(date));
		var calendarData = loadCalendarData(formatDateSlash(date));
		displayCalendar(calendarData);
	}

	var loadCalendarData = function(date) {
		$.ajax({
			url: "/loadCalendar",
			type: "GET",
			dataType: "json",
			data: {
				targetDate: date,
				_csrf: $("*[name=_csrf]").val()
			}
		}).done(function(data) {
			displayCalendar(data);
		}).fail(function() {
			alert("error!");
		});
	}

	var displayCalendar = function(datalist) {
		// カレンダーのデータを除去
		$("#calendarDays").find("tr").each(function() {
			$(this).find("td").each(function() {
				$(this).find("a").text("");
			});
		});

		for (var i = 0; i < 5; i++) {
			for (var j = 0; j < 7; j++) {
				$("#calendarDays").find("tr")
					.eq(i)
					.find("td")
					.eq(j)
					.find("a")
					.text(datalist[i].weekday[j]);
			}

		}

	}

	$('.showTimeline').on('click', function() {
		hideDiary();
		hideSearchArea();
		clearInputContent();
		hideEditArea();
		showTimeline();
	});

	var showTimeline = function() {
		loadTimeline();
	}

	var loadTimeline = function() {
		$.ajax({
			url: "/loadCalendar",
			type: "GET",
			dataType: "json",
			data: {
				targetDate: date,
				_csrf: $("*[name=_csrf]").val()
			}
		}).done(function(data) {
			displayTimeline(data);
		}).fail(function() {
			alert("error!");
		});
	}

	var displayTimeline = function(datalist) {

	}

	// 以下、カレンダーの操作関数
	// 入力した文字数をカウントして表示する
	$(".input").on("keyup", function(e) {
		var num = this.id.slice(-1);
		var count = $(this).find("textarea").val().length;
		$("#count" + num).text(count);
	});
});