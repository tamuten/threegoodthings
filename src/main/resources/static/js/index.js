$(function() {
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
			e.preventDefault();
			$.ajax({
				url: "/register",
				type: "POST",
				dataType: "json",
				data: {
					good: $('#good' + num).val(),
					date: $("#date").val(),
					num: num,
					_csrf: $("*[name=_csrf]").val()
				}
			}).done(function(data) {
				alert("登録が完了しました");
				$(".input").hide();
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
				if(data == null) return;
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
				var displayDate = new Date(data.date);
				var year = displayDate.getFullYear();
				var month = displayDate.getMonth() + 1;
				var day = displayDate.getDate();
				$(".date").find('p').text(year + '年' + month + '月' + day + '日');
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
				$("#date").val(year + '/' + month + '/' + day);
			}).fail(function() {
				alert("error!");
			});
		})
	})();

	// 入力した文字数をカウントして表示する
	$(".input").on("keyup", function(e) {
		var num = this.id.slice(-1);
		var count = $(this).find("textarea").val().length;
		$("#count" + num).text(count);
	});
});

function yesterday() {
	document.myForm.setAttribute("action", "/yesterday");
	document.myForm.setAttribute("method", "get");
	document.myForm.submit();
}

function tomorrow() {
	document.myForm.setAttribute("action", "/tomorrow");
	document.myForm.setAttribute("method", "get");
	document.myForm.submit();
}