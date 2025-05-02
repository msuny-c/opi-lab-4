document.addEventListener('DOMContentLoaded', function () {
	setScrollShadow('header', 'shadow', 5);
	setGraphListener();
	setHelpPointListener();
});

function saveAttemptValues(x, y, r, type) {
	saveAttempt([{ name: 'x', value: x }, { name: 'y', value: y }, { name: 'r', value: r }, {name: 'type', value: type}]);
}

function setScrollShadow(selector, shadow, px) {
	window.addEventListener('scroll', function () {
		if (
			document.body.scrollTop > px ||
			document.documentElement.scrollTop > px
		) {
			document.querySelector(selector).classList.add(shadow);
		} else {
			document.querySelector(selector).classList.remove(shadow);
		}
	});
}

function processForm() {
	if (!isValidForm()) return;
	document.querySelector(".error-messages").classList.add('none');
	let r = getFormValues().r;
	updateRadius(r);
}

function processRadius() {
	let r = getFormValues().r;
	redraw();
	updateRadius(r);
}

function getFormValues() {
	let x = document.getElementById('form:x');
	let y = document.getElementById('form:y_input');
	let r = document.getElementById('form:r_input');
	let type = document.getElementById('form:Ttype_input');
	return {x: x.value, y: y.value, r: r.value != 0 ? r.value : 0.1, type: type.value};
}

function isValidForm() {
	let coordinates = getFormValues();
	return validateParameters(coordinates.x, coordinates.y, coordinates.r);
}

function drawPoint(x, y, r, success) {
	let svg = document.querySelector('svg');
	let point = createPoint();
	if (success == 'true') point.setAttribute('fill', 'red')
	else point.setAttribute('fill', 'black');
	point.setAttribute('cx', 300 / 2 + (x * 100) / r);
	point.setAttribute('cy', 300 / 2 - (y * 100) / r);
	svg.appendChild(point);
}

function validateParameters(x, y, r) {
	let validateMin = function(name, input, min, max) {
		if (input < min || input > max) {
			error(name.toUpperCase() + ` должен быть в пределах от ${min} до ${max} включительно!`);
			return false;
		}
		return true;
	}
	if (!validateMin('x', x, -4, 4)) return false;
	if (!validateMin('y', y, -3, 5)) return false;
	if (!validateMin('r', r, 0.1, 3)) return false;
	return true;
}

function error(message) {
	document.querySelector(".error-messages").classList.remove('none');
	document.querySelector('.error').innerHTML = message;
}

function createPoint() {
	let point = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
	point.setAttribute('visibility', 'visible');
	point.setAttribute('r', 5);
	return point;
}

// Код для графика

function updateRadius(r) {
	document.querySelectorAll('.r').forEach((el) => (el.textContent = r));
	document.querySelectorAll('.-r').forEach((el) => (el.textContent = -r));
	document.querySelectorAll('.r-2').forEach((el) => (el.textContent = r / 2));
	document.querySelectorAll('.-r-2').forEach((el) => (el.textContent = -r / 2));
}

function setGraphListener() {
	let svg = document.querySelector('svg');
	let dot = document.getElementById('help-point');
	svg.addEventListener('mousemove', function (event) {
		let mouse = getSvgCoordinates(event);
		dot.setAttribute('cx', mouse.x);
		dot.setAttribute('cy', mouse.y);
		dot.setAttribute('visibility', 'visible');
	});
	svg.addEventListener('mouseleave', function () {
		dot.setAttribute('visibility', 'hidden');
	})
}

function getSvgCoordinates(event) {
	const svg = document.querySelector('svg');
	const point = svg.createSVGPoint();
	point.x = event.clientX;
	point.y = event.clientY;
	return point.matrixTransform(svg.getScreenCTM().inverse());
}

function setHelpPointListener() {
	let point = document.getElementById('help-point');
	point.addEventListener('click', function () {
		let coordinates = getFormValues();
		let cx = point.getAttribute('cx');
		let cy = point.getAttribute('cy');
		let r = round(coordinates.r, 1);
		let x = round((cx - 300 / 2) * r / 100, 1);
		let y = (300 / 2 - cy) * r / 100;
		if (!validateParameters(x, y, r)) return;
		document.querySelector(".error-messages").classList.add('none');
		updateRadius(r);
		saveAttemptValues(x, y, r, coordinates.type);
	});
}

function redrawPoints(data) {
	let points = document.querySelector('svg').querySelectorAll('circle');
	for (let point of points) {
		if (point.id != 'help-point') {
			point.remove();
		}
	}
	for (let attempt of data) {
		drawPoint(attempt.x, attempt.y, getFormValues().r, attempt.success);
	}
}

function updateDatesToTimezone() {
	const dateElements = document.querySelectorAll('.date-column');
    dateElements.forEach((element) => {
		const utcDate = element.innerHTML.trim();
		const localDate = new Date(utcDate);
		element.innerHTML = localDate.toLocaleString();
    });
}

function drawPointAfter() {
	const rows = document.querySelectorAll("table tbody tr");
	const cells = rows[rows.length - 1].querySelectorAll('td')
	let x = cells[1].innerHTML;
	let y = cells[2].innerHTML;
	let r = cells[3].innerHTML;
	let success = cells[5].querySelector('span');
	drawPoint(x, y, r, success.innerHTML == 'попадание' ? 'true' : 'false');
}

function round(value, precision) {
    var multiplier = Math.pow(10, precision || 0);
    return Math.round(value * multiplier) / multiplier;
}