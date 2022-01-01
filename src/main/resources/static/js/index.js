$(function() {
	// 以下、日記の操作関数
	(function() {
		var num = 0;
		var val = null;
		$(".diaryBtn").on("click", function(e) {
			$(".searchMode").hide();
			displayDiary();
			cancel();
		});

		$(".searchBtn").on("click", function(e) {
			$(".searchMode").show();
			hideDiary();
			cancel();
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
			cancel();
			$(".display").show();
			$(".move").show();
		});

		function cancel() {
			$("#input" + num).find("textarea").val(val);
			$("#count" + num).text(val.length);
			$(".input").hide();
			$(".error").hide();
			$(".edit").hide();
		}

		function displayDiary() {
			$(".date-move").show();
			$(".display").show();
			$(".move").show();
		}

		function hideDiary() {
			$(".date-move").hide();
			$(".display").hide();
			$(".move").hide();
		}

		$("#save").on("click", function(e) {
			var good = $('#good' + num).val();
			if (good == '') {
				// TODO Springの仕様で空文字になってしまう しかしWebDataBinderをコピーしただけではダメ
				// Resolved [org.springframework.web.bind.MissingServletRequestParameterException:
				// Required request parameter 'good' for method parameter type String is present but converted to null]
				good = null;
			} else
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
				$(".input").hide();
				$(".error").hide();
				$(".edit").hide();
				displayDiary();
				loadDiary();
			}).fail(function() {
				alert("error!");
			});
		});

		function loadDiary() {
			$.ajax({
				url: "/load",
				type: "GET",
				dataType: "json",
				data: {
					date: $("#date").val(),
					_csrf: $("*[name=_csrf]").val()
				}
			}).done(function(data) {
				if (data == null) return;
				if (data.good1 != null) {
					$("#goodText1").text(data.good1);
					$("#goodText1").show();
					$("#edit1").hide();
				}
				if (data.good2 != null) {
					$("#goodText2").text(data.good2);
					$("#goodText2").show();
					$("#edit2").hide();
				}
				if (data.good3 != null) {
					$("#goodText3").text(data.good3);
					$("#goodText3").show();
					$("#edit3").hide();
				}
			}).fail(function() {
				alert("error!");
			});
		}

		$(".yesterday").on("click", function(e) {
			e.preventDefault();
			$.ajax({
				url: "/yesterday",
				type: "GET",
				dataType: "json",
				data: {
					date: $("#date").val(),
					_csrf: $("*[name=_csrf]").val()
				}
			}).done(function(data) {
				displayData(data);
			}).fail(function() {
				alert("error!");
			});
		})

		$(".tomorrow").on("click", function(e) {
			e.preventDefault();
			$.ajax({
				url: "/tomorrow",
				type: "GET",
				dataType: "json",
				data: {
					date: $("#date").val(),
					_csrf: $("*[name=_csrf]").val()
				}
			}).done(function(data) {
				displayData(data);
			}).fail(function() {
				alert("error!");
			});
		})

		function displayData(data) {
			var displayDate = new Date(data.date);
			var year = displayDate.getFullYear();
			var month = displayDate.getMonth() + 1;
			var day = displayDate.getDate();
			$(".date").find('p').text(year + '年' + month + '月' + day + '日');
			if (data.good1 != null) {
				$("#goodText1").text(data.good1);
				$("#goodText1").show();
				$("#edit1").hide();
			} else {
				$("#goodText1").hide();
				$("#edit1").show();
			}
			if (data.good2 != null) {
				$("#goodText2").text(data.good2);
				$("#goodText2").show();
				$("#edit2").hide();
			} else {
				$("#goodText2").hide();
				$("#edit2").show();
			}
			if (data.good3 != null) {
				$("#goodText3").text(data.good3);
				$("#goodText3").show();
				$("#edit3").hide();
			} else {
				$("#goodText3").hide();
				$("#edit3").show();
			}
			$("#date").val(year + '/' + month + '/' + day);
		}
	})();

	var dt = null;
	$("#last-year").on("click", function(e) {
		dt = new Date($("#cal-date").val());
		dt.setFullYear(dt.getFullYear() - 1);
		var year = dt.getFullYear();
		var month = dt.getMonth() + 1;
		var day = dt.getDate();
		$(".cal-title").find("p").text(year + '年' + month + '月');
		loadCalendarData(year + '/' + month + '/' + day);
	});

	function loadCalendarData(date) {
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

	function displayCalendar(data) {

		// カレンダーのデータを除去
		$("#calendarDays").find("tr").each(function() {
			$(this).find("td").each(function() {
				$(this).text("");
			});
		});
	}

	// 以下、カレンダーの操作関数
	// 入力した文字数をカウントして表示する
	$(".input").on("keyup", function(e) {
		var num = this.id.slice(-1);
		var count = $(this).find("textarea").val().length;
		$("#count" + num).text(count);
	});
});