var chai = require('chai');
var expect = require('chai').expect;
var dddata = require('../test/paituli-ui-test');

describe('sortDropdownData', function () {
	
	var shortest = "shortest";
	var ascending = "ascending";
	var newest = "newest";
	
	var inputStrings = ['aaa', 'ddd', 'bbb', 'ccc']; //lowerCase, upperCase, empty, different types etc
	var inputShortDates = ['2017', '2015', '2014', '2016']
	var inputLongDates = ['31.12.2014', '30.12.2014', '1.01.2015'] //toDate?
	var inputScalesHa = ['3 ha' ,'5 ha', '0.5 ha']
	var inputScalesOne = ['1:10 000', '1:100 000'] //mixed, various, etc
	var inputScalesTwo = ['1:100 000', '1:10 000']
	var inputScalesTilesOne = ['30 m x 30 m', '20 m x 20 m' ,'30 m x 20 m']
	var inputScalesTilesTwo = ['30 m x 30 m', '20 m x 20 m' ,'20 m x 30 m']
	var inputScalesMixedCaseOne = ['30 m x 30 m', '20 m x 20 m' ,'5 ha']
	var inputScalesMixedCaseTwo = ['30 m x 30 m', '1:10 000' ,'20 m x 30 m']
	var inputScalesMixedCaseThree = ['30 m x 30 m', '1:10 000' ,'3 ha']
	var outputStrings;
	
	it ('strings are sorted alphabetically', function () {
		outputStrings = dddata.sortData(ascending, inputStrings);
		
		expect(outputStrings[0]).to.equal('aaa');
		expect(outputStrings[1]).to.equal('bbb');
		expect(outputStrings[2]).to.equal('ccc');
		expect(outputStrings[3]).to.equal('ddd');
		expect(outputStrings[1]).to.not.equal('ddd');

	})

	it ('short dates are sorted from newest to oldest', function () {
		outputStrings = dddata.sortData(newest, inputShortDates);
		console.log(outputStrings.toString())

		expect(outputStrings[0]).to.equal('2017');
		expect(outputStrings[1]).to.equal('2016');
		expect(outputStrings[2]).to.equal('2015');
		expect(outputStrings[3]).to.equal('2014');
	})

	it ('long dates are sorted from newest to oldest', function () {
		outputStrings = dddata.sortData(newest, inputLongDates);
		console.log(outputStrings.toString())

		expect(outputStrings[0]).to.equal('1.01.2015');
		expect(outputStrings[1]).to.equal('31.12.2014');
		expect(outputStrings[2]).to.equal('30.12.2014');
	})

	it ('scales in ha are sorted by shortness', function () {
		outputStrings = dddata.sortData(shortest, inputScalesHa);

		expect(outputStrings[0]).to.equal('0.5 ha');
		expect(outputStrings[1]).to.equal('3 ha');
		expect(outputStrings[2]).to.equal('5 ha');
	})

	it ('scales are sorted by shortness (1)', function () {
		outputStrings = dddata.sortData(shortest, inputScalesOne);

		expect(outputStrings[0]).to.equal('1:10 000');
		expect(outputStrings[1]).to.equal('1:100 000');
	})

	it ('scales are sorted by shortness (2)', function () {
		outputStrings = dddata.sortData(shortest, inputScalesTwo);

		expect(outputStrings[0]).to.equal('1:10 000');
		expect(outputStrings[1]).to.equal('1:100 000');
	})

	it ('scales tiles are sorted by shortness (1)', function () {
		outputStrings = dddata.sortData(shortest, inputScalesTilesOne);

		expect(outputStrings[0]).to.equal('20 m x 20 m');
		expect(outputStrings[1]).to.equal('30 m x 20 m');
		expect(outputStrings[2]).to.equal('30 m x 30 m');
	})

	it ('scales tiles are sorted by shortness (2)', function () {
		outputStrings = dddata.sortData(shortest, inputScalesTilesTwo);

		expect(outputStrings[0]).to.equal('20 m x 20 m');
		expect(outputStrings[1]).to.equal('20 m x 30 m');
		expect(outputStrings[2]).to.equal('30 m x 30 m');
	})

	it ('scales ha are sorted by shortness', function () {
		outputStrings = dddata.sortData(shortest, inputScalesHa);

		expect(outputStrings[0]).to.equal('0.5 ha');
		expect(outputStrings[1]).to.equal('3 ha');
		expect(outputStrings[2]).to.equal('5 ha');
	})

	it ('scales mixed case are sorted by shortness (1)', function () {
		outputStrings = dddata.sortData(shortest, inputScalesMixedCaseOne);

		expect(outputStrings[0]).to.equal('0.5 ha');
		expect(outputStrings[1]).to.equal('3 ha');
		expect(outputStrings[2]).to.equal('5 ha');
	})

	it ('scales mixed case are sorted by shortness (2)', function () {
		outputStrings = dddata.sortData(shortest, inputScalesMixedCaseTwo);

		expect(outputStrings[0]).to.equal('0.5 ha');
		expect(outputStrings[1]).to.equal('3 ha');
		expect(outputStrings[2]).to.equal('5 ha');
	})

	it ('scales mixed case are sorted by shortness (3)', function () {
		outputStrings = dddata.sortData(shortest, inputScalesMixedCaseThree);

		expect(outputStrings[0]).to.equal('0.5 ha');
		expect(outputStrings[1]).to.equal('3 ha');
		expect(outputStrings[2]).to.equal('5 ha');
	})
})


























