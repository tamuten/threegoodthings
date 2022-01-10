
function formatDateSlash(date) {
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	return ('0000' + year).slice(-4) + '/' + ('00' + month).slice(-2) + '/' + ('00' + day).slice(-2);
}

function formatDateKanjiForDate(date) {
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	return ('0000' + year).slice(-4) + '年' + ('00' + month).slice(-2) + '月' + ('00' + day).slice(-2) + '日';
}

function formatDateKanji(year, month, day){
	return ('0000' + year).slice(-4) + '年' + ('00' + month).slice(-2) + '月' + ('00' + day).slice(-2) + '日';
}

function setDateYesterday(date) {
	date.setDate(date.getDate() - 1);
	return date;
}

function setDateTomorrow(date) {
	date.setDate(date.getDate() + 1);
	return date;
}

function formatMonthForDate(date){
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	return ('0000' + year).slice(-4) + '年' + ('00' + month).slice(-2) + '月';
}

function formatMonth(year, month){
	return ('0000' + year).slice(-4) + '年' + ('00' + month).slice(-2) + '月';
}