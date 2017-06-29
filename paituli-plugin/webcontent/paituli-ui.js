/**
 * Created by jmlehtin on 19/12/2016.
 */

METADATA_API = "/paituli-portlet/paituliAPI.jsp";

hakaUser = false;
geoserver_username = '';
geoserver_password = '';
currentIndexMapLayer = null;

FINNISH_LANGUAGE = "fi_FI";
ENGLISH_LANGUAGE = "en_US";
USED_LANGUAGE = Liferay.ThemeDisplay.getLanguageId();

metadata = null;

function getUrlParameter(param) {
	var pageURL = window.location.search.substring(1);
	var urlVariables = pageURL.split('&');
	for (var i = 0; i < urlVariables.length; i++) {
		var parameterName = urlVariables[i].split('=');
		if (parameterName[0] == param) {
			return parameterName[1];
		}
	}
	return null;
}

pageDataIdParam = getUrlParameter("data_id");

//If the user is logged in with HAKA, let's set ready GeoServer's username and password for paituli_protected datasets
function checkAccessRights(){
	hakaUser = Liferay.ThemeDisplay.isSignedIn();
	if (hakaUser){
		$.ajax({ 'url' : '/secure/files/geoserverp.txt',
			'dataType': 'json',
			'success' : function(result) {
				geoserver_username=result.username;
				geoserver_password=result.pwd;
				var testurl = 'https://avaa.tdata.fi/geoserver/paituli_protected/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=paituli_protected:il_temp_monthly_stat&maxFeatures=1&outputFormat=application%2Fjson';

				$.ajax(
					{
						'password' : geoserver_password,
						'username' : geoserver_username,
						'url'      : testurl,
						'type'     : 'GET',
						'success'  : function() { console.log("log in success")  },
						'error'    : function(err) { console.log("log in not successful")  },
					}
				);
			}
		});
	} else{
		hakaUser = false;
		geoserver_username='';
		geoserver_password='';
	}
}

function checkParameterDatasetAccess() {
	loadMetadata(function() {
		if (pageDataIdParam === null || pageDataIdParam.length == 0) {
			main();
		} else {
			var dataIdRow = alasql("SELECT * FROM ? WHERE data_id='" + pageDataIdParam + "'", [metadata]).map(function (item) {
				return item;
			});
			if (typeof dataIdRow[0] !== 'undefined') {
				if (dataIdRow[0].access == 2 && !hakaUser) {
					window.location.replace('https://' + window.location.host + '/c/portal/login?p_l_id= ' + Liferay.ThemeDisplay.getPlid());
				} else {
					main();
				}
			} else {
				main();
			}
		}
	});
}

function loadMetadata(afterMetadataLoadCallback) {
	$.getJSON(METADATA_API + "?lang=" + USED_LANGUAGE, function(data) {
		metadata = data;
		afterMetadataLoadCallback();
	});
}

var loadIndexMapFeatures = function(response) {
	currentIndexMapLayer.getSource().addFeatures(currentIndexMapLayer.getSource().readFeatures(response));
};

var main = function() {
	String.prototype.insert = function (index, string) {
		if (index > 0)
			return this.substring(0, index) + string + this.substring(index, this.length);
		else
			return string + this;
	};

	$(document).tooltip({track: true});

	function Translator(language) {
		this.language = language;
		var translations = {
			appHeader: {
				fi_FI: "PaITuli - Paikkatietoja tutkimukseen ja opetukseen",
				en_US: "PaITuli - Spatial data for research and teaching"
			},
			data: {
				header: {
					fi_FI: "Valitse aineisto:",
					en_US: "Select dataset:"
				},
				producer: {
					fi_FI: "Tuottaja:",
					en_US: "Producer:"
				},
				data: {
					fi_FI: "Aineisto:",
					en_US: "Data:"
				},
				scale: {
					fi_FI: "Mittakaava:",
					en_US: "Scale:"
				},
				year: {
					fi_FI: "Vuosi:",
					en_US: "Year:"
				},
				format: {
					fi_FI: "Formaatti:",
					en_US: "Format:"
				},
				coordSys: {
					fi_FI: "Koordinaatisto:",
					en_US: "Coordinate system:"
				},
				metadata: {
					fi_FI: "Metatiedot",
					en_US: "Metadata"
				},
				search: {
					fi_FI: "Hae karttalehtiä",
					en_US: "Search mapsheets"
				},
				searchresult: {
					fi_FI: "Löydettiin !features! karttalehteä",
					en_US: "Found !features! map sheets"
				},
				toomanyresults: {
					fi_FI: "Löydettiin liian monta karttalehteä. Rajaa hakua tarkemmaksi.",
					en_US: "Found too many map sheets. Please search more specifically."
				}
			},
			info: {
				download: {
					fi_FI: "Lataa aineisto",
					en_US: "Download dataset"
				},
				files: {
					fi_FI: "Tiedostot",
					en_US: "Files"
				},
				documents: {
					fi_FI: "Dokumentit",
					en_US: "Documents"
				},
				licence: {
					fi_FI: "Käyttöehdot",
					en_US: "Licence terms"
				},
				downloadindex: {
					fi_FI: "Lataa karttalehtijako Shape tiedostona",
					en_US: "Download index as Shape file"
				},
				info: {
					fi_FI: "Valitse karttalehdet kartalta tai hae karttalehtia nimen perusteella. Karttalehtien valitsemiksi kartalta aktivoi ensin karttalehtien valinnan työkalu ja raaha kartalle sopivaan paikkaan nelikulmio tai klikkaa kartalla tarvitsemasi karttalehtiä. Jo valittuja karttalehtiä voi poistaa valinnasta valitsemalla niitä uudestaan.",
					en_US: "Select area of interest from the map or search map sheets by name. For selecting map sheets from the map first activate the map sheets selection tool and then draw a rectangle to a suitable area or click map sheets one by one. Already selected map sheets may be removed from selection by selecting them again."
				},
				downloadtab: {
					fi_FI: "Ladattavat tiedostot",
					en_US: "Files for download"
				},
				featureinfotab: {
					fi_FI: "Kohdetiedot",
					en_US: "Feature info"
				},
				metadatatab: {
					fi_FI: "Metatiedot",
					en_US: "Metadata"
				},
				metadatainfo: {
					fi_FI: "Tämän aineiston <b>kaikki metatiedot</b> löytyvät <a href='!metadata_url!' target='_blank'>Etsin-hakupalvelusta</a>.",
					en_US: "<b>All metadata</b> for this dataset is available from <a href='!metadata_url!' target='_blank'>Etsin metadata service</a>."
				},
				metadatacontentheader: {
					fi_FI: "<h6>Aineiston kuvaus</h6>",
					en_US: "<h6>Description of dataset</h6>"
				},
				metadatalinksheader: {
					fi_FI: "<h6>Aineistoa kuvaavat tiedostot</h6>",
					en_US: "<h6>Files describing the dataset</h6>"
				},
				nometadataavailable: {
					fi_FI: "Aineiston kuvaus ei ole saatavilla",
					en_US: "Dataset description not available"
				},
				featureinfodefault: {
					fi_FI: "Valitse Info-työkalu ja klikkaa karttaa",
					en_US: "Select Info tool and click on map"
				},
				maxfeaturewarning: {
					fi_FI: "Latauksen kokorajoitus on 2 Gb. Korkeintaan !maxFeatures! karttalehteä voidaan valita.",
					en_US: "Download limit is 2 Gb. Maximum of !maxFeatures! map sheets may be selected."
				}
			},
			map: {
				basemap: {
					fi_FI: "Taustakartta",
					en_US: "Background map"
				},
				indexmap: {
					fi_FI: "Indeksikartta",
					en_US: "Index map"
				},
				datamap: {
					fi_FI: "Aineisto",
					en_US: "Data"
				},
				catchment: {
					fi_FI: "Valuma-alueet",
					en_US: "Catchment areas"
				},
				municipalitiesmap: {
					fi_FI: "Kuntajako",
					en_US: "Municipalities"
				},
				reset: {
					fi_FI: "Näytä koko Suomi",
					en_US: "Zoom to Finland"
				},
				pan: {
					fi_FI: "Siirrä karttaa hiirellä raahaamalla",
					en_US: "Pan, move the map with dragging the mouse"
				},
				select: {
					fi_FI: "Valitse karttalehtiä kartalta, raahaamalla nelikulmio tai klikkaamalla",
					en_US: "Select map sheets from the map by drawing a rectangle or clicking"
				},
				info: {
					fi_FI: "Info, katso kohteiden ominaisuustietoja klikkaamalla",
					en_US: "Info, see attribute data by clicking"
				},
				clearSelection: {
					fi_FI: "Poista kaikki karttalehdet valinnasta",
					en_US: "Deselect all map sheets"
				},
				dataAvailabilityWarning: {
					fi_FI: "Aineiston esikatselu ei ole saatavilla",
					en_US: "Data preview is not available"
				},
				resolutionwarning: {
					fi_FI: "Lähennä karttaa nähdäksesi aineiston",
					en_US: "Zoom in to see the data"
				},
				locationsearch: {
					fi_FI: "Etsi sijaintia...",
					en_US: "Search for a location..."
				},
				locationNotFound: {
					fi_FI: "Annetulla haulla ei löytynyt sijantia",
					en_US: "The provided query did not find any related location"
				}
			},
			email: {
				modalheader: {
					fi_FI: "Lähetä latauslinkki sähköpostiini",
					en_US: "Send data download link to my e-mail"
				},
				datasetinfo: {
					fi_FI: "Valittu aineisto",
					en_US: "Selected dataset"
				},
				inputsheader: {
					fi_FI: "Tiedot lataamista varten",
					en_US: "Information for downloading"
				},
				emailfield: {
					fi_FI: "Sähköpostiosoite",
					en_US: "E-mail"
				},
				emailfieldPlaceholder: {
					fi_FI: "esim@toinen.fi",
					en_US: "example@other.com"
				},
				licencefield: {
					fi_FI: "Hyväksyn aineiston <a href='!licence!' target='_blank'>käyttöehdot</a>",
					en_US: "I accept the <a href='!licence!' target='_blank'>licence terms</a>"
				},
				info: {
					fi_FI: "Lähetettyäsi tilauksen saat hetken kuluttua sähköpostiisi latauslinkin.",
					en_US: "In a moment after sending the download order, you will receive an e-mail with a download link."
				},
				sendButton: {
					fi_FI: "Lähetä latauslinkki sähköpostiini",
					en_US: "Send data download link to my e-mail"
				},
				cancelButton: {
					fi_FI: "Peruuta",
					en_US: "Cancel"
				},
				errorEmailLength: {
					fi_FI: "Sähköpostiosoite puuttuu",
					en_US: "E-mail is missing"
				},
				errorEmailFormat: {
					fi_FI: "Virheellinen sähköpostiosoite",
					en_US: "E-mail address invalid"
				},
				errorCheckboxChecked: {
					fi_FI: "Käyttöehtojen hyväksyminen on pakollista",
					en_US: "Accepting the licence terms is mandatory"
				}
			}
		}

		var byString = function(o, s) {
			s = s.replace(/\[(\w+)\]/g, '.$1'); // convert indexes to properties
			s = s.replace(/^\./, '');           // strip a leading dot
			var a = s.split('.');
			for (var i = 0, n = a.length; i < n; ++i) {
				var k = a[i];
				if (k in o) {
					o = o[k];
				} else {
					return;
				}
			}
			return o;
		}

		this.getVal = function(field) {
			return byString(translations, field + "." + this.language);
		}
	}



	var translator = new Translator(USED_LANGUAGE);

	var panSelectBtn = $('#panselection-button');
	var selectSelectContainer = $('#selectselection-container');
	var selectSelectBtn = $('#selectselection-button');
	var clearSelectContainer = $('#clearselection-container');
	var clearSelectBtn = $('#clearselection-button');
	var infoSelectContainer = $('#infoselection-container');
	var infoSelectBtn = $('#infoselection-button');
	selectSelectContainer.hide();
	clearSelectContainer.hide();
	infoSelectContainer.hide();

	var locationSearchInput = $('#location-search-input');

	var currentIndexMapLabelLayer = null;
	var currentDataLayerSrc = null;
	var currentDataLayer = null;
	var currentDataId = null;
	var currentDataUrl = null;
	var currentMaxResolution = null;

	var mapContainerId = 'map-container';
	var layerCheckboxContainer = $('#layer-checkbox-container');

	//Etsin
	var ETSIN_BASE = "//etsin.avointiede.fi" //"//etsin-demo.avointiede.fi"
	var ETSIN_METADATA_JSON_BASE_URL =  ETSIN_BASE +"/api/3/action/package_show?id=";
	var ETSIN_METADATA_BASE_URL = ETSIN_BASE +"/fi/dataset/";
	var ETSIN_METADATA_BASE_URL_EN = ETSIN_BASE +"/en/dataset/";
	//var  = ETSIN_BASE +"/storage/f/paituli/ehdot/";
	var LICENCE_INTERNAL_PATH_FROM_BASE_FOLDER = "ehdot/";

	//GeoServer
	var BASE_URL = "//avaa.tdata.fi/geoserver/" //"//avoin-test.csc.fi/geoserver/";
	var INDEX_LAYER = "paituli:index";
	var LAYER_NAME_MUNICIPALITIES = "paituli:mml_hallinto_2014_100k";
	var LAYER_NAME_CATCHMENT_AREAS = "paituli:syke_valuma_maa";

	var WFS_INDEX_MAP_LAYER_URL = BASE_URL + "wfs?service=WFS&version=2.0.0&request=GetFeature&srsname=epsg:3857&typeNames=" + INDEX_LAYER + "&cql_filter=BBOX(geom,!extent!,'EPSG:4326') AND !key! = '!value!'";
	var WMS_INDEX_MAP_LABEL_LAYER_URL = BASE_URL + "wms?service=WMS&LAYERS= " + INDEX_LAYER + "&CQL_FILTER=data_id = '!value!'";
	var WMS_PAITULI_BASE_URL = BASE_URL + "wms?";
	var WMS_PAITULI_BASE_URL_GWC = BASE_URL + "gwc/service/wms?";
	var WFS_INDEX_MAP_DOWNLOAD_SHAPE = BASE_URL + "wfs?service=WFS&version=2.0.0&request=GetFeature&srsname=epsg:4326&typeNames=" + INDEX_LAYER + "&outputFormat=shape-zip&propertyname=label,geom&cql_filter= !key! = '!value!'";

	//Paituli APIs
	var GENERATE_PACKAGE_API_URL = "/paituli-portlet/generatePackageAPI.jsp";


	//Location search
	var NOMINATIM_API_URL = "//nominatim.openstreetmap.org/search?format=json&q=!query!&addressdetails=0&limit=1";
	var MAX_DOWNLOADABLE_SIZE = 2000;


	var prevSelectedTab = null;

	var emailModal = null;
	var emailForm = null;
	var emailRegex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
	var emailInput = $("#email-input");
	var licenceCheckbox = $("#licence-checkbox");
	var tips = $("#email-modal-tips");
	var fileList = [];
	var fileLabelList = [];

	function updateModalTips(t) {
		tips.text(t).addClass("ui-state-highlight");
		setTimeout(function() {
			tips.removeClass("ui-state-highlight", 1500);
		}, 500);
	}

	function checkLength(obj, min, max, errMsg) {
		if (obj.val().length > max || obj.val().length < min ) {
			obj.addClass("ui-state-error");
			updateModalTips(errMsg);
			return false;
		} else {
			return true;
		}
	}

	function checkRegexp(obj, regexp, errMsg) {
		if(!(regexp.test(obj.val()))) {
			obj.addClass("ui-state-error");
			updateModalTips(errMsg);
			return false;
		} else {
			return true;
		}
	}

	function checkIsChecked(obj, errMsg) {
		if(!(obj.prop('checked'))) {
			obj.addClass("ui-state-error");
			updateModalTips(errMsg);
			return false;
		} else {
			return true;
		}
	}

	function emailData() {
		var filesToPackagePaths = getSemicolonStrFromStrArray(fileList);
		var filesToPackageNames = getSemicolonStrFromStrArray(fileLabelList);
		var emailVal = emailInput.val();
		var url = GENERATE_PACKAGE_API_URL;

		if(filesToPackagePaths && emailVal) {
			var postParams = "";
			postParams = postParams.concat("filepaths=" + filesToPackagePaths + "&email=" + emailVal);
			if(filesToPackageNames) {
				postParams = postParams.concat("&filenames=" + filesToPackageNames);
			}
			var org = getCurrentLayerData('org');
			if(org) {
				postParams = postParams.concat("&org=" + org);
			}
			var name = getCurrentLayerData('name');
			if(name) {
				postParams = postParams.concat("&data=" + name);
			}
			var scale = getCurrentLayerData('scale');
			if(scale) {
				postParams = postParams.concat("&scale=" + scale);
			}
			var year = getCurrentLayerData('year');
			if(year) {
				postParams = postParams.concat("&year=" + year);
			}
			var coordsys = getCurrentLayerData('coord_sys');
			if(coordsys) {
				postParams = postParams.concat("&coordsys=" + coordsys);
			}
			var format = getCurrentLayerData('format');
			if(format) {
				postParams = postParams.concat("&format=" + format);
			}
			if(currentDataId) {
				postParams = postParams.concat("&data_id=" + currentDataId);
			}

			// Validate input fields
			var valid = true;
			emailInput.removeClass("ui-state-error");
			licenceCheckbox.removeClass("ui-state-error");
			valid = valid && checkLength(emailInput, 1, 80, translator.getVal("email.errorEmailLength"));
			valid = valid && checkRegexp(emailInput, emailRegex, translator.getVal("email.errorEmailFormat"));
			valid = valid && checkIsChecked(licenceCheckbox, translator.getVal("email.errorCheckboxChecked"));

			if (valid) {
				emailModal.data('email', emailInput.val());
				$.post(url, postParams, function(data, status) {
					emailModal.dialog("close");
				});
			}
			return valid;
		} else {
			console.error("No email or file paths defined!");
			return false;
		}
	}

	emailModal = $('#email-modal').dialog({
		autoOpen: false,
		height: 'auto',
		width: 600,
		modal: true,
		closeOnEscape: true,
		draggable: true,
		resizable: false,
		title: translator.getVal("email.modalheader"),
		buttons: [
			{
				text: translator.getVal("email.sendButton"),
				icons: {
					primary: "ui-icon-mail-closed"
				},
				click: emailData,
				type: "submit"
			},
			{
				text: translator.getVal("email.cancelButton"),
				icons: {
					primary: "ui-icon-close"
				},
				click: function() {$(this).dialog("close");}
			}
		],
		close: function() {
			emailForm[0].reset();
			emailInput.removeClass("ui-state-error");
			licenceCheckbox.removeClass("ui-state-error");
		}
	});

	emailForm = emailModal.find("form");
	emailForm.on("submit", function(event) {
		event.preventDefault();
		emailData();
	});

	function setHtmlElementTextValues() {
		$('#dl-service-header h1').text(translator.getVal("appHeader"));
		$('#data-form legend').text(translator.getVal("data.header"));
		$('#resetview-button').attr("title", translator.getVal("map.reset"));
		$('#clearselection-button').attr("title", translator.getVal("map.clearSelection"));
		$('#panselection-button').attr("title", translator.getVal("map.pan"));
		$('#selectselection-button').attr("title", translator.getVal("map.select"));
		$('#infoselection-button').attr("title", translator.getVal("map.info"));
		$('#download-container-anchor').text(translator.getVal("info.downloadtab"));
		$('#feature-info-container-anchor').text(translator.getVal("info.featureinfotab"));
		$('#metadata-container-anchor').text(translator.getVal("info.metadatatab"));
		locationSearchInput.attr("placeholder", translator.getVal("map.locationsearch"));
		$('#email-input-label').text(translator.getVal('email.emailfield'));
		$('#email-input').attr('placeholder', translator.getVal('email.emailfieldPlaceholder'));
		$('#email-modal-form fieldset legend').text(translator.getVal('email.inputsheader'));
		$('#email-instructions').text(translator.getVal('email.info'));
	}

	setHtmlElementTextValues();
	var tabContainerId = 'info-container';
	var tabContainer = $('#' + tabContainerId);
	var downloadTabContentRootId = 'download-container';
	var downloadTabContentRoot = $('#' + downloadTabContentRootId);
	var featureInfoTabContentRootId = 'feature-info-container';
	var featureInfoTabContentRoot = $('#' + featureInfoTabContentRootId);
	var metadataTabContentRootId = 'metadata-container';
	var metadataTabContentRoot = $('#' + metadataTabContentRootId);
	tabContainer.tabs({
		activate: function(event, ui) {
			prevSelectedTab = ui.newPanel.get(0).id;
		}
	});

	function strStartsWith(str, prefix) {
		return str.indexOf(prefix) === 0;
	}

	function setInfoContent(contentType, params) {
		var rootElem = null;
		switch(contentType) {
			case "download":
				createDownloadContent(downloadTabContentRoot);
				break;
			case "featureinfo":
				clearFeatureInfoTabContent();
				createFeatureInfoContent(featureInfoTabContentRoot, params);
				break;
			case "metadata":
				clearMetadataTabContent();
				createMetadataTabContent();
			default:
				break;
		}
	}

	var featureSearchContainer = $('#feature-search-container');

	function createSearchField() {
		var searchBtn = $('<a>', {
			'class': 'btn btn-default',
			id: 'search-button',
			'href': ""
		});
		searchBtn.text(translator.getVal("data.search"));
		searchBtn.on('click', searchFeatures);

		var searchField = $('<input>', {
			id: 'feature-search-field',
			'type': 'search'
		});
		searchField.keyup(function(e) {
			if(e.keyCode == 13){
				searchBtn.click();
				this.blur();
			}
		});
		searchField.focus(function(e) {
			clearSearchResults();
		});

		var searchResults = $('<div>', {
			id: 'feature-search-results',
		});

		searchField.appendTo(featureSearchContainer);
		searchBtn.appendTo(featureSearchContainer);
		searchResults.appendTo(featureSearchContainer);
	}

	function searchFeatures() {
		var searchStr = $('#feature-search-field').val();
		if(searchStr !== null && searchStr.length > 0) {
			clearMapFeatureSelection();
			clearSearchResults();
			var features = getSearchResultFeatures(searchStr);
			var maxFeatures = getMaxDownloadableFeatureAmount();
			if(features.length > maxFeatures) {
				$('#feature-search-results').text(translator.getVal("data.toomanyresults"));
			} else {
				selectedFeatures.extend(features);
				$('#feature-search-results').text(translator.getVal("data.searchresult").replace('!features!', features.length));
				setInfoContent('download');
			}
		}
		return false;
	}

	function createFeatureInfoContent(rootElem, event) {
		var viewResolution = view.getResolution();
		//var wmsSource = getFeatureInfoWmsSrc(currentDataUrl);
		var url = currentDataLayerSrc.getGetFeatureInfoUrl(
			event.coordinate, viewResolution, 'EPSG:3857',
			{	'INFO_FORMAT': 'text/plain',
				'outputFormat': 'text/javascript'
			}
		);
		if (url) {
			rootElem.html('<iframe id="feature-info-iframe" seamless src="' + url + '"></iframe>');
		}
	}

	function createDownloadContent(rootElem) {
		var dlButton = $('#download-button');
		if(!dlButton.length) {
			dlButton = $('<button>', {
				'class': 'btn btn-default',
				id: 'download-button'
			});
		}
		dlButton.text(translator.getVal("info.download") + ": ~" + getTotalDownloadSize() + " Mb");

		var licenceHeader = $('#download-licence-header');
		if(!licenceHeader.length) {
			licenceHeader = $('<h5>', {
				id: 'download-licence-header',
				'class': 'download-tab-header'
			});
		}
		licenceHeader.text(translator.getVal("info.documents"));

		var licenceUrl = getCurrentLayerData('license_url');
		var dlLicInputId = 'download-licence-input';
		var dlLicContainer = $('#download-licence-container');
		var dlLicInput = $('#' + dlLicInputId);
		var dlLicLabelLink = $('#download-licence-link');
		if(!dlLicInput.length) {
			dlLicContainer = $('<div>', {
				id: 'download-licence-container'
			});
			dlLicLabelLink = $('<a>', {
				id: 'download-licence-link',
				'href': licenceUrl,
				'target': '_blank',
				'class': 'download-label',
				'data-value': translator.getVal("info.licence"),

			});
			dlLicLabelLink.text(translator.getVal("info.licence"));
			dlLicInput = $('<input>', {
				'checked': 'checked',
				id: dlLicInputId,
				'type': 'checkbox',
				'value': LICENCE_INTERNAL_PATH_FROM_BASE_FOLDER + licenceUrl,
				'class': 'download-checkbox'
			});
			dlLicInput.appendTo(dlLicContainer);
			dlLicLabelLink.appendTo(dlLicContainer);
		}
		var dlIndexButton = $('#download-index-button');
		if(!dlIndexButton.length) {
			dlIndexButton = $('<button>', {
				'class': 'btn btn-default',
				id: 'downloadindex-button'
			});
		}
		dlIndexButton.text(translator.getVal("info.downloadindex"));
		dlIndexButton.on("click", function(event) {
			var url = WFS_INDEX_MAP_DOWNLOAD_SHAPE.replace('!key!', 'data_id').replace('!value!', currentDataId);
			window.open(url,'_blank');
		});

		var downloadFilesHeader = $('<h5>', {
			id: 'download-file-header',
			'class': 'download-tab-header'
		});
		downloadFilesHeader.text(translator.getVal("info.files"));

		if(selectedFeatures.getLength() > 0) {
			var dataListContainerElem = $('#data-download-list');
			if(!dataListContainerElem.length) {
				clearDownloadTabContent();
				dlButton.appendTo(rootElem);
				dlIndexButton.appendTo(rootElem);
				licenceHeader.appendTo(rootElem);

				dlLicContainer.appendTo(rootElem);


				downloadFilesHeader.appendTo(rootElem);
			}

			if(!dataListContainerElem.length) {
				dataListContainerElem = $('<div>', {
					id: 'data-download-list'
				});
			} else {
				dataListContainerElem.empty();
			}

			var i=0;
			fileLabelList = [];
			var dlLabelList = [];

			selectedFeatures.forEach(function(feature, idx, array) {
				var label = feature.get('label');
				fileLabelList.push(label);
				var filePath = feature.get('path');
				i += 1;
				var inputId = 'download-file-input-' + i.toString();
				var dlLabel = $('<label>', {
					'for': inputId,
					'class': 'download-label',
					'data-value': label
				});
				var dlInput = $('<input>', {
					'checked': 'checked',
					id: inputId,
					'type': 'checkbox',
					'value': filePath,
					'class': 'download-checkbox'
				});
				dlInput.on('change', function() {
					updateSelectedFeatures(feature, dlInput)
					updateDownloadFileList(dlButton, dlLicInput)
				});
				dlLabel.append(dlInput);
				dlLabel.append(label);
				dlLabelList.push(dlLabel);
			});
			dlLabelList.sort(function(a, b) {
				if(a.attr('data-value') >= b.attr('data-value')) {
					return 1;
				} else {
					return -1;
				}
			});
			$.each(dlLabelList, function(idx, val) {
				val.appendTo(dataListContainerElem);
			});
			if(i > 0) {
				dataListContainerElem.appendTo(rootElem);
			}
		} else {
			clearDownloadTabContent();
			dlButton.appendTo(rootElem);
			dlIndexButton.appendTo(rootElem);
			licenceHeader.appendTo(rootElem);
			dlLicContainer.appendTo(rootElem);
			downloadFilesHeader.appendTo(rootElem);
			var downloadInfo = $('<div>', {
				id: 'download-info-container'
			});
			downloadInfo.text(translator.getVal('info.info'));
			downloadInfo.appendTo(rootElem);
		}
		dlLicInput.on('change', function() {
			updateDownloadFileList(dlButton, dlLicInput);
			updateFileLabelListForLicence(dlLicInput, licenceUrl);
		});
		dlButton.on("click", function(event) {
			event.preventDefault();
			event.stopImmediatePropagation();
			updateEmailModal();
			$('#email-modal-tips').empty();
			$('#email-input').val(emailModal.data('email') === null ? "" : emailModal.data('email'));
			if(dlLicInput.prop('checked') ? (fileList.length > 1) : (fileList.length > 0)) {
				emailModal.dialog("open");
			}
		});
		updateDownloadFileList(dlButton, dlLicInput);
		updateFileLabelListForLicence(dlLicInput, licenceUrl);
	}

	function updateFileLabelListForLicence(dlLicInput, licenceUrl) {
		var licenceIdx = fileLabelList.indexOf(licenceUrl);
		if(dlLicInput.prop('checked')) {
			if(licenceIdx == -1) {
				fileLabelList.push(licenceUrl);
			}
		} else {
			if(licenceIdx > -1) {
				fileLabelList.splice(licenceIdx, 1);
			}
		}
	}

	function updateDownloadFileList(dlButton, dlLicInput) {
		fileList = [];
		var markedForDownload = $('.download-checkbox:checked');
		markedForDownload.each(function(i, checkbox) {
			fileList.push(checkbox.value);
		});

		updateDownloadButton(dlButton, dlLicInput);
	}

	function getSemicolonStrFromStrArray(strArray) {
		var retStr = "";
		if(strArray.length > 0) {
			$.each(strArray, function(i, str) {
				if(i == 0) {
					retStr = "";
				}
				retStr = retStr + str + ";";
			});
			retStr = retStr.substring(0, retStr.length-1);
		}
		return retStr;
	}

	function updateDownloadButton(dlButton, dlLicInput) {
		dlButton.text(translator.getVal("info.download") + ": ~" + getTotalDownloadSize() + " Mb");
		if(dlLicInput.prop('checked') ? (fileList.length > 1) : (fileList.length > 0)) {
			dlButton.prop('disabled', false);
		} else {
			dlButton.prop('disabled', true);
		}
	}

	function updateSelectedFeatures(clickedFeature, dlInput) {
		if(dlInput.is(':checked')) {
			selectedFeatures.push(clickedFeature);
		} else {
			selectedFeatures.remove(clickedFeature);
			dlInput.parent().remove();
		}
	}

	function updateEmailModal() {
		var dataDescrContainer = $('#data-description');
		dataDescrContainer.empty();

		$('#licence-checkbox-label').html(translator.getVal('email.licencefield').replace('!licence!', getCurrentLayerData('license_url')));
		var dataDescrContent = $('<div>');
		dataDescrContent.text(translator.getVal('email.datasetinfo') + ": " + getCurrentLayerData('org') + ", " + getCurrentLayerData('name') + ", " + getCurrentLayerData('scale') + ", " + getCurrentLayerData('year') + ", " + getCurrentLayerData('coord_sys') + ", " + getCurrentLayerData('format') + ": " + getTotalDownloadSize() + " Mb");
		dataDescrContent.appendTo(dataDescrContainer);
	}

	function clearFeatureInfoTabContent() {
		featureInfoTabContentRoot.empty();
	}

	function clearDownloadTabContent() {
		downloadTabContentRoot.empty();
	}

	function clearMetadataTabContent() {
		metadataTabContentRoot.empty();
	}

	function clearInfoBoxTabs() {
		clearDownloadTabContent();
		clearFeatureInfoTabContent();
	}

	function selectTab(tabIndex) {
		tabContainer.tabs("option", "active", tabIndex);
	}

	function selectTabAfterDatasetChange(hasInfoTab) {
		if(prevSelectedTab == null) {
			prevSelectedTab = downloadTabContentRootId;
		}

		var newTabId = null;
		if(prevSelectedTab == downloadTabContentRootId) {
			newTabId = downloadTabContentRootId;
		} else if(prevSelectedTab == featureInfoTabContentRootId) {
			if(hasInfoTab) {
				newTabId = featureInfoTabContentRootId;
			} else {
				newTabId = downloadTabContentRootId;
			}
		} else if(prevSelectedTab == metadataTabContentRootId) {
			newTabId = metadataTabContentRootId;
		}
		var index = $('#' + tabContainerId + ' a[href="#' + newTabId + '"]').parent().index();
		$('#' + tabContainerId).tabs("option", "active", index);
	}

	function clearSearchResults() {
		$('#feature-search-field').val('');
		$('#feature-search-results').empty();
	}

	/*
	 function clearSearchField() {
	 featureSearchContainer.empty();
	 }
	 */

	function initFormInputs(formRootElemId) {
		var producerInputId = 'producer-input';
		var dataInputId = 'data-input';
		var scaleInputId = 'scale-input';
		var yearInputId = 'year-input';
		var formatInputId = 'format-input';
		var coordsysInputId = 'coordsys-input';
		var rootElem = $('#' + formRootElemId)

		var producerInputRow = $('<article>', {
			class: 'form-input-row',
			id: 'producer-row'
		});
		var producerLabel = $('<div>', {
			class: 'form-input-label',
			id: 'producer-label'
		});
		producerLabel.append(translator.getVal("data.producer"));

		var producerInput = $('<select>', {
			class: 'form-input',
			id: producerInputId
		});
		producerLabel.appendTo(producerInputRow);
		producerInput.appendTo(producerInputRow);

		var dataInputRow = $('<article>', {
			class: 'form-input-row',
			id: 'data-row'
		});
		var dataLabel = $('<div>', {
			class: 'form-input-label',
			id: 'data-label'
		});
		dataLabel.append(translator.getVal("data.data"));

		var dataInput = $('<select>', {
			class: 'form-input',
			id: dataInputId
		});
		dataLabel.appendTo(dataInputRow);
		dataInput.appendTo(dataInputRow);

		var scaleInputRow = $('<article>', {
			class: 'form-input-row',
			id: 'scale-row'
		});
		var scaleLabel = $('<div>', {
			class: 'form-input-label',
			id: 'scale-label'
		});
		scaleLabel.append(translator.getVal("data.scale"));
		var scaleInput = $('<select>', {
			class: 'form-input',
			id: scaleInputId
		});
		scaleLabel.appendTo(scaleInputRow);
		scaleInput.appendTo(scaleInputRow);

		var yearInputRow = $('<article>', {
			class: 'form-input-row',
			id: 'year-row'
		});
		var yearLabel = $('<div>', {
			class: 'form-input-label',
			id: 'year-label'
		});
		yearLabel.append(translator.getVal("data.year"));
		var yearInput = $('<select>', {
			class: 'form-input',
			id: yearInputId
		});
		yearLabel.appendTo(yearInputRow);
		yearInput.appendTo(yearInputRow);

		var formatInputRow = $('<article>', {
			class: 'form-input-row',
			id: 'format-row'
		});
		var formatLabel = $('<div>', {
			class: 'form-input-label',
			id: 'format-label'
		});
		formatLabel.append(translator.getVal("data.format"));
		var formatInput = $('<select>', {
			class: 'form-input',
			id: formatInputId
		});
		formatLabel.appendTo(formatInputRow);
		formatInput.appendTo(formatInputRow);

		var coordsysInputRow = $('<article>', {
			class: 'form-input-row',
			id: 'coordsys-row'
		});
		var coordsysLabel = $('<div>', {
			class: 'form-input-label',
			id: 'coordsys-label'
		});
		coordsysLabel.append(translator.getVal("data.coordSys"));
		var coordsysInput = $('<select>', {
			class: 'form-input',
			id: coordsysInputId
		});
		coordsysLabel.appendTo(coordsysInputRow);
		coordsysInput.appendTo(coordsysInputRow);

		producerInputRow.appendTo(rootElem);
		dataInputRow.appendTo(rootElem);
		scaleInputRow.appendTo(rootElem);
		yearInputRow.appendTo(rootElem);
		formatInputRow.appendTo(rootElem);
		coordsysInputRow.appendTo(rootElem);

		$('#' + producerInputId).on('change', function() {
			updateDataList(producerInput, dataInput);
		});
		$('#' + dataInputId).on('change', function() {
			updateScaleList(producerInput, dataInput, scaleInput);
		});
		$('#' + scaleInputId).on('change', function() {
			updateYearList(producerInput, dataInput, scaleInput, yearInput);
		});
		$('#' + yearInputId).on('change', function() {
			updateFormatList(producerInput, dataInput, scaleInput, yearInput, formatInput);
		});
		$('#' + formatInputId).on('change', function() {
			updateCoordsysList(producerInput, dataInput, scaleInput, yearInput, formatInput, coordsysInput);
		});
		$('#' + coordsysInputId).on('change', function() {
			var selectedProducer = producerInput.val();
			var selectedData = dataInput.val();
			var selectedScale = scaleInput.val();
			var selectedYear = yearInput.val();
			var selectedFormat = formatInput.val();
			var selectedCoordsys = coordsysInput.val();
			var dataIdResult = alasql("SELECT data_id FROM ? WHERE org='" + selectedProducer + "' AND name='" + selectedData + "' AND scale='" + selectedScale + "' AND year='" + selectedYear + "' AND format='" + selectedFormat + "' AND coord_sys='" + selectedCoordsys + "'", [metadata]).map(function(item) {
				return item.data_id;
			});
			if(typeof dataIdResult[0] !== 'undefined') {
				currentDataId = dataIdResult[0];
				var dataUrl = getCurrentLayerData('data_url');

				if(dataUrl !== null) {
					currentDataUrl = dataUrl;
				} else {
					currentDataUrl = null;
				}
			} else {
				currentDataId = null;
			}
			updateMap();

		});

		createSearchField();

		if(pageDataIdParam !== null) {
			updateProducerList(producerInput)
			setDataIdVars(producerInput, dataInput, scaleInput, yearInput, formatInput, coordsysInput);
		} else {
			updateProducerList(producerInput);
		}
	}

	function setDataIdVars(producerInput, dataInput, scaleInput, yearInput, formatInput, coordsysInput) {
		var dataIdRow = alasql("SELECT * FROM ? WHERE data_id='" + pageDataIdParam +"'", [metadata]).map(function(item) {
			return item;
		});
		if(typeof dataIdRow[0] !== 'undefined') {
			var producer = dataIdRow[0].org;
			var dataName = dataIdRow[0].name;
			var scale = dataIdRow[0].scale;
			var year = dataIdRow[0].year;
			var format = dataIdRow[0].format;
			var coordsys = dataIdRow[0].coord_sys;
			producerInput.val(producer);
			producerInput.trigger("change");
			dataInput.val(dataName);
			dataInput.trigger("change");
			scaleInput.val(scale);
			scaleInput.trigger("change");
			yearInput.val(year);
			yearInput.trigger("change");
			formatInput.val(format);
			formatInput.trigger("change");
			coordsysInput.val(coordsys);
			coordsysInput.trigger("change");
		}
		pageDataIdParam = null;
	}

	function updateProducerList(producerInput) {
		//hakaUser = Liferay.ThemeDisplay.isSignedIn();
		var producers;
		if(hakaUser) {
			producers = alasql("SELECT DISTINCT org FROM ? ", [metadata]).map(function(item) {
				return item.org;
			});
		} else {
			producers = alasql("SELECT DISTINCT org FROM ? WHERE access=1", [metadata]).map(function(item) {
				return item.org;
			});
		}

		updateOptions(producerInput, sortDropdownData("ascending", producers), true);
	}

	function updateDataList(producerInput, dataInput) {
		var selectedProducerId = producerInput.val();
		if(!strStartsWith(selectedProducerId, "--")) {
			//var isSignedIn = Liferay.ThemeDisplay.isSignedIn();
			var data;
			if(hakaUser) {
				data = alasql("SELECT name FROM ? WHERE org='" + selectedProducerId + "' GROUP BY name", [metadata]).map(function(item) {
					return item;
				});
			} else {
				data = alasql("SELECT name FROM ? WHERE org='" + selectedProducerId + "' AND access=1 GROUP BY name", [metadata]).map(function(item) {
					return item;
				});
			}

			var dataNames = sortDropdownData("ascending", $.map(data, function(item, i) {
				return item.name;
			}));
			updateOptions(dataInput, dataNames, false);
		} else {
			addEmptyOption(dataInput);
		}
	}

	function updateScaleList(producerInput, dataInput, scaleInput) {
		var selectedProducerId = producerInput.val();
		var selectedDataId = dataInput.val();
		if(!strStartsWith(selectedDataId, "--")) {
			var scales = alasql("SELECT DISTINCT scale FROM ? WHERE org='" + selectedProducerId + "' AND name='" + selectedDataId + "'", [metadata]).map(function(item) {
				return item.scale;
			});
			updateOptions(scaleInput, sortDropdownData("shortest", scales), false);
		} else {
			addEmptyOption(scaleInput);
		}
	}

	function updateYearList(producerInput, dataInput, scaleInput, yearInput) {
		var selectedProducerId = producerInput.val();
		var selectedDataId = dataInput.val();
		var selectedScaleId = scaleInput.val();
		if(!strStartsWith(selectedScaleId, "--")) {
			var years = alasql("SELECT DISTINCT year FROM ? WHERE org='" + selectedProducerId + "' AND name='" + selectedDataId + "' AND scale='" + selectedScaleId + "'", [metadata]).map(function(item) {
				return item.year;
			});
			updateOptions(yearInput, sortDropdownData("newest", years), false);
		} else {
			addEmptyOption(yearInput);
		}
	}

	function updateFormatList(producerInput, dataInput, scaleInput, yearInput, formatInput) {
		var selectedProducerId = producerInput.val();
		var selectedDataId = dataInput.val();
		var selectedScaleId = scaleInput.val();
		var selectedYearId = yearInput.val();
		if(!strStartsWith(selectedYearId, "--")) {
			var formats = alasql("SELECT DISTINCT format FROM ? WHERE org='" + selectedProducerId + "' AND name='" + selectedDataId + "' AND scale='" + selectedScaleId + "' AND year='" + selectedYearId + "'", [metadata]).map(function(item) {
				return item.format;
			});
			updateOptions(formatInput, sortDropdownData("ascending", formats), false);
		} else {
			addEmptyOption(formatInput);
		}
	}

	function updateCoordsysList(producerInput, dataInput, scaleInput, yearInput, formatInput, coordsysInput) {
		var selectedProducerId = producerInput.val();
		var selectedDataId = dataInput.val();
		var selectedScaleId = scaleInput.val();
		var selectedYearId = yearInput.val();
		var selectedFormatId = formatInput.val();
		if(!strStartsWith(selectedFormatId, "--")) {
			var coordsyses = alasql("SELECT DISTINCT coord_sys FROM ? WHERE org='" + selectedProducerId + "' AND name='" + selectedDataId + "' AND scale='" + selectedScaleId + "' AND year='" + selectedYearId + "' AND format='" + selectedFormatId + "'", [metadata]).map(function(item) {
				return item.coord_sys;
			});
			updateOptions(coordsysInput, sortDropdownData("ascending", coordsyses), false);
		} else {
			addEmptyOption(coordsysInput);
		}
	}

	function addEmptyOption(inputElem) {
		inputElem.empty();
		var title = "--"
		var optionElem = $('<option>', {
			'value': title
		});
		optionElem.text(title);
		inputElem.append(optionElem);
		inputElem.prop('disabled', true);
		inputElem.val($('#' + inputElem.attr('id') + ' option:first').val()).change();
	}

	function updateOptions(inputElem, optionNames, isProducerInput, optionIds) {
		if(optionIds === undefined) {
			optionIds = null;
		}
		// Remove old values
		inputElem.empty();
		inputElem.prop('disabled', false);
		// Set new ones
		if(isProducerInput) {
			var title = null;
			if(USED_LANGUAGE == FINNISH_LANGUAGE) {
				title = "--Valitse aineistotuottaja--";
			} else if(USED_LANGUAGE == ENGLISH_LANGUAGE) {
				title = "--Select data producer--";
			}
			var optionElem = $('<option>', {
				'value': title
			});
			optionElem.text(title);
			inputElem.append(optionElem);
		}
		$.each(optionNames, function(idx, value) {
			var optionElem = $('<option>', {
				'value': value
			});
			optionElem.text(value);
			if(optionIds !== null) {
				optionElem.attr('id', optionIds[idx]);
			}
			inputElem.append(optionElem);
		});

		if(inputElem.find('option').size() <= 1) {
			inputElem.prop('disabled', true);
		}

		inputElem.val($('#' + inputElem.attr('id') + ' option:first').val()).change();
	}

	function sortDropdownData(type, data) {
		switch(type) {
			case "ascending":
				data.sort();
				break;
			case "newest": //Used for dates
				data.sort(function(a, b) {
					/*
					 if(!a.matches("[0-9]+") && !b.matches("[0-9]+")) {
					 return
					 }
					 */
					var c;
					var d;
					// Compare dates, if dates are in style 2000-2005
					if(a.indexOf("-") != -1) {
						c = a.substring(a.indexOf("-")+1);
					} else {
						c = a;
					}
					if(b.indexOf("-") != -1) {
						d = b.substring(b.indexOf("-")+1);
					} else {
						d = b;
					}
					// Compare dates, if date includes day of month, count only the year anyway.
					if(a.indexOf(".") != -1) {
						c = a.substring(a.lastIndexOf(".")+1);
					} else {
						c = a;
					}
					if(b.indexOf(".") != -1) {
						d = b.substring(b.lastIndexOf(".")+1);
					} else {
						d = b;
					}
					return d-c;
				});
				break;
			case "shortest":
			//Used for scales
			//The scales are basicallly ordered in numeric order from smaller to bigger.
			//The first split is for cases like: 1:10 000, 25mx25m, "1:20 000, 1:50 000".
				data.sort(function(a, b) {
					var c;
					var d;
					var parts;

					if(a.search(/[?,:\.xX-]+/) != -1) {
						parts = a.split(/[?,:\.xX-]+/g);
						c = parts[parts.length - 1];
					} else {
						c = a;
					}
          c= c.replace(/\D/g,'');

					if(b.search(/[?,:\.xX-]+/) != -1) {
						parts = b.split(/[?,:\.xX-]+/g);
						d = parts[parts.length - 1];
					} else {
						d = b;
					}
					d= d.replace(/\D/g,'');

					return c-d;
				});
				break;
			default:
				return null;
		}
		return data;
	}

	function createMetadataTabContent() {
		var metadataURN = getCurrentLayerData('meta');
		var metadataInfoLabel = $('<div>', {
			id: 'metadata-info-label'
		});
		if(metadataURN !== null) {
			var metadataBaseUrl;
			if(USED_LANGUAGE == FINNISH_LANGUAGE) {
				metadataBaseUrl = ETSIN_METADATA_BASE_URL;
			} else if(USED_LANGUAGE == ENGLISH_LANGUAGE) {
				metadataBaseUrl = ETSIN_METADATA_BASE_URL_EN;
			}
			metadataInfoLabel.append(translator.getVal("info.metadatainfo").replace('!metadata_url!', metadataBaseUrl + metadataURN));
			metadataTabContentRoot.append(metadataInfoLabel);
		}

		var metadataNotes = $('<div>', {
			id: 'metadata-notes'
		});

		var errorFunction = function(metadataNotes) {
			metadataNotes.html(translator.getVal('info.nometadataavailable'));
			metadataTabContentRoot.append(metadataNotes);
		};

		var successFunction = function(rawEtsinMetadata, metadataNotes) {
			var notesHtml = getNotesAsHtmlFromEtsinMetadata(rawEtsinMetadata);
			var linksHtml = getLinksAsHtmlFromEtsinMetadata(rawEtsinMetadata);
			if(rawEtsinMetadata == null || notesHtml == null) {
				metadataNotes.html(translator.getVal('info.nometadataavailable'));
			} else {
				metadataNotes.html(translator.getVal('info.metadatacontentheader') + notesHtml +  linksHtml);
			}
			if(metadataTabContentRoot.children().length >= 2) {
				metadataTabContentRoot.children().last().remove();
			}
			metadataTabContentRoot.append(metadataNotes);
		};

		fetchMetadataDescription(metadataURN, metadataNotes, successFunction, errorFunction);
	}

	function getLinksAsHtmlFromEtsinMetadata(rawEtsinMetadata) {
		if(rawEtsinMetadata != null) {
			var etsinLinks = '<br>' + translator.getVal('info.metadatalinksheader') + "<ul>";
			$.each(rawEtsinMetadata.result.resources, function (key, data) {
				if(data.name != null){
					etsinLinks = etsinLinks  + '<li><a href="' + data.url + '" target="_blank">' + data.name + '</a></li>';
				}
			});
			etsinLinks = etsinLinks  + "</ul>";
			if (etsinLinks.indexOf("href") > 0){
				return etsinLinks;
			}
		}

		return null;
	}

	function getNotesAsHtmlFromEtsinMetadata(rawEtsinMetadata) {
		if(rawEtsinMetadata != null) {
			var notes = JSON.parse(rawEtsinMetadata.result.notes);
			if(USED_LANGUAGE == FINNISH_LANGUAGE) {
				notes = notes.fin;
			} else if(USED_LANGUAGE == ENGLISH_LANGUAGE) {
				notes = notes.eng;
			}
			if(notes == null) {
				return null;
			}

			var regexp = /\[.*\]\(http.*\)/g;
			var match, matches = [];

			while ((match = regexp.exec(notes)) != null) {
				matches.push(match.index);
			}

			matches.reverse();
			$.each(matches, function(loopIdx, matchIdx) {
				notes = notes.insert(matchIdx+1, '<a href="' + notes.substring(notes.indexOf('(', matchIdx)+1, notes.indexOf(')', matchIdx)) + '" target="_blank">');
				notes = notes.insert(notes.indexOf('\]', matchIdx), '</a>');
				notes = notes.replace(notes.substring(notes.indexOf('(', matchIdx), notes.indexOf(')', matchIdx)+1), '');
			});
			notes = notes.replace(/\[|\]/g, '');

			return notes.replace(/(\r\n|\n|\r)/gm,"<br>");
		}
		return null;
	}

	function fetchMetadataDescription(metadataURN, metadataNotes, successFn, errorFn) {
		var result = null;
		$.ajax({
			url: ETSIN_METADATA_JSON_BASE_URL + metadataURN,
			success: function(data) {
				successFn(data, metadataNotes);
			},
			error: function() {
				errorFn(metadataNotes);
			}
		});
	}

	function setFeatureInfoTabDefaultContent() {
		var featureInfoDefaultLabel = $('<div>', {
			id: 'feature-info-default-label'
		});
		featureInfoDefaultLabel.append(translator.getVal("info.featureinfodefault"));
		featureInfoTabContentRoot.append(featureInfoDefaultLabel);
	}

	var isFirstTimeLoaded = true;

	function updateMap() {
		map.removeLayer(currentIndexMapLayer);
		map.removeLayer(currentIndexMapLabelLayer);
		map.removeLayer(currentDataLayer);
		locationSearchInput.val('');
		clearMapFeatureSelection();
		clearInfoBoxTabs();
		//clearSearchField();
		clearSearchResults();
		$('#feature-search-field').value='';
		if(currentDataId != null) {
			setInfoContent("metadata");
			setFeatureInfoTabDefaultContent();
			loadIndexLayer();
			loadIndexMapLabelLayer();
			if(currentIndexMapLayer !== null) {
				currentIndexMapLayer.getSource().on('change', function(e) {
					var hasInfoTab = layerHasFeatureInfo();
					if (this.getState() == 'ready' && isFirstTimeLoaded) {
						toggleMapControlButtonsVisibility(true);
						if(getTotalAmountOfLayerFeatures() > 1) {
							featureSearchContainer.css("visibility","visible");
						} else if(getTotalAmountOfLayerFeatures() == 1) {
							selectedFeatures.push(currentIndexMapLayer.getSource().getFeatures()[0]);
							featureSearchContainer.css("visibility","hidden");
						}
						setInfoContent('download');

						isFirstTimeLoaded = false;
					}
					selectTabAfterDatasetChange(hasInfoTab);
				});
				if(currentIndexMapLabelLayer !== null) {
					currentIndexMapLayer.on('change:visible', function(e) {
						if(currentIndexMapLayer.getVisible()) {
							currentIndexMapLabelLayer.setVisible(true);
						} else {
							currentIndexMapLabelLayer.setVisible(false);
						}
					});
				}

				var maxScaleResult = getCurrentLayerData('data_max_scale');
				if(maxScaleResult !== null) {
					currentMaxResolution = getMapResolutionFromScale(parseInt(maxScaleResult));
				} else {
					currentMaxResolution = null;
				}

				loadDataLayer();
				if(currentDataLayer !== null) {
					map.getLayers().insertAt(1, currentDataLayer);
					clearMapWarning()
				}
				else{
					setDataAvailabiltyWarning();
				}
				map.addLayer(currentIndexMapLayer);
				if(currentIndexMapLabelLayer !== null) {
					map.addLayer(currentIndexMapLabelLayer);
				}
				//Kylli, without next 3 rows, the warning of previously selected dataset was visible.
				if(currentMaxResolution != null){
					setMaxResolutionWaringing();
				}
			}
			tabContainer.show();
		} else {
			tabContainer.hide();
			toggleMapControlButtonsVisibility(false);
		}
	}

	function layerHasFeatureInfo() {
		return getCurrentLayerData('data_url') !== null;
	}

	function toggleMapControlButtonsVisibility(isAnyDataSelected) {

		if(isAnyDataSelected) {

			selectSelectContainer.show();
			clearSelectContainer.show();

			if(layerHasFeatureInfo()) {
				infoSelectContainer.show();
				$('#info-container ul li:nth-child(2)').show();
			} else {
				if(infoSelectBtn.hasClass("active")) {
					panSelectBtn.click();
				}
				infoSelectContainer.hide();
				$('#info-container ul li:nth-child(2)').hide();
			}

		} else {
			selectSelectContainer.hide();
			clearSelectContainer.hide();
			infoSelectContainer.hide();
		}
	}

	function getCurrentLayerData(field) {
		var value = alasql("SELECT " + field + " FROM ? WHERE data_id='" + currentDataId + "'", [metadata]).map(function(item) {
			return item[field];
		});
		if(typeof value !== 'undefined' && value !== null && typeof value[0] !== 'undefined' && value[0] !== null) {
			return value[0];
		} else {
			return null;
		}
	}

	function flashLayerPanel(duration) {
		var layerPanel = $('.layer-switcher .panel');
		var layerSwitcher = $('.layer-switcher');
		layerSwitcher.trigger('mouseover');
		layerPanel.fadeOut(duration, function() {
			layerPanel.removeAttr('style');
			layerSwitcher.trigger('mouseout');
		});
	}

	function loadIndexMapLabelLayer() {
		if(currentDataId !== null) {
			var url = WMS_INDEX_MAP_LABEL_LAYER_URL.replace('!value!', currentDataId);
			var src = new ol.source.ImageWMS({
				url: url,
				params: {'VERSION': '1.1.1'},
				serverType: 'geoserver'
			});

			currentIndexMapLabelLayer = new ol.layer.Image({
				source: src,
				visible: true
			});
		} else {
			currentIndexMapLabelLayer = null;
		}

	}

	function loadIndexLayer() {
		if(currentDataId !== null) {
			var url = WFS_INDEX_MAP_LAYER_URL.replace('!key!', 'data_id').replace('!value!', currentDataId);
			var src = new ol.source.ServerVector({
				format: new ol.format.GeoJSON(),
				loader: function(extent, resolution, projection) {
					$.ajax({
						url: url.replace("!extent!", extent.join(',')) + '&outputFormat=text/javascript&format_options=callback:loadIndexMapFeatures',
						dataType: 'jsonp'
					});
				},
				projection: 'EPSG:3857'
			});
			currentIndexMapLayer = new ol.layer.Vector({
				title: translator.getVal("map.indexmap"),
				source: src,
				visible: true,
				style: new ol.style.Style({
					stroke: new ol.style.Stroke({
						color: 'rgba(0, 0, 255, 1.0)',
						width: 2
					})
				})
			});
		} else {
			currentIndexMapLayer = null;
		}
		isFirstTimeLoaded = true;
	}

	function loadDataLayer() {
		if(currentDataId !== null && currentDataUrl !== null) {

			//Set baseurl correctly for password protected datasets.
			var url = WMS_PAITULI_BASE_URL_GWC;
			if(currentDataUrl.indexOf("protected") > -1){
				url = WMS_PAITULI_BASE_URL;

				currentDataLayerSrc = new ol.source.ImageWMS({
					url: url,
					params: {'LAYERS': currentDataUrl,'VERSION': '1.1.1'},
					serverType: 'geoserver'
				});

				currentDataLayer = new ol.layer.Image({
					title: translator.getVal("map.datamap"),
					source: currentDataLayerSrc,
					visible: true
				});
			}
			else{
				currentDataLayerSrc = new ol.source.TileWMS({
					url: url,
					params: {'LAYERS': currentDataUrl,'VERSION': '1.1.1'},
					serverType: 'geoserver'
				});

				currentDataLayer = new ol.layer.Tile({
					title: translator.getVal("map.datamap"),
					source: currentDataLayerSrc,
					visible: true
				});
			}

			/*
			 //Set baseurl correctly for password protected datasets.
			 var url = WMS_PAITULI_BASE_URL_GWC;
			 if(currentDataUrl.indexOf("protected") > -1){
			 url = WMS_PAITULI_BASE_URL;
			 }


			 currentDataLayerSrc = new ol.source.TileWMS({
			 url: url,
			 params: {'LAYERS': currentDataUrl,'VERSION': '1.1.1'},
			 serverType: 'geoserver'
			 });

			 currentDataLayer = new ol.layer.Tile({
			 title: translator.getVal("map.datamap"),
			 source: currentDataLayerSrc,
			 visible: true
			 });
			 */

			if(currentMaxResolution !== null) {
				currentDataLayer.setMaxResolution(currentMaxResolution);
			}
		} else {
			currentDataLayer = null;
		}
	}

	function getMapResolutionFromScale(scale) {
		return scale / 2835;
	}

	//KYlli, not needed?
	/*
	 var loadIndexMapLabelFeatures = function(response) {
	 currentIndexMapLabelLayer.getSource().addFeatures(currentIndexMapLabelLayer.getSource().readFeatures(response));
	 };
	 */

	function getSearchResultFeatures(searchStr) {
		var hits = [];
		currentIndexMapLayer.getSource().forEachFeature(function(feature) {
			if(feature.get('label').toLowerCase().indexOf(searchStr.toLowerCase()) != -1) {
				hits.push(feature);
			}
		});
		return hits;
	}

	function getTotalAmountOfLayerFeatures() {
		return currentIndexMapLayer.getSource().getFeatures().length;
	}

	var osmLayer = new ol.layer.Tile({
		title: translator.getVal("map.basemap"),
		source : new ol.source.TileWMS({
			url: "http://ows.terrestris.de/osm/service?",
			attributions: [
				new ol.Attribution({
					html: '© <a target="_blank" href="http://ows.terrestris.de/dienste.html">terrestris</a>. Data: © <a target="_blank" href="http://www.openstreetmap.org/copyright">OpenStreetMap contributors</a>'
				})
			],
			params: {
				'LAYERS': "OSM-WMS",
				'VERSION': '1.1.0'
			}
		}),
		opacity : 1.0,
		visible: true
	});

	var municipalitiesLayer = new ol.layer.Tile({
		title: translator.getVal("map.municipalitiesmap"),
		source : new ol.source.TileWMS({
			url : WMS_PAITULI_BASE_URL,
			params : {
				'LAYERS' : LAYER_NAME_MUNICIPALITIES,
				'SRS' : 'EPSG:3067',
				'VERSION' : '1.1.0'
			}
		}),
		opacity: 1.0,
		visible : false
	});

	var catchmentLayer = new ol.layer.Tile({
		title: translator.getVal("map.catchment"),
		source : new ol.source.TileWMS({
			url : WMS_PAITULI_BASE_URL,
			params : {
				'LAYERS' : LAYER_NAME_CATCHMENT_AREAS,
				'SRS' : 'EPSG:2393',
				'VERSION' : '1.1.0'
			}
		}),
		opacity: 1.0,
		visible : false
	});

	function toggleMapLayer(evt, layer) {
		var isNowChecked = evt.target.checked;
		if(layer !== null) {
			layer.setVisible(isNowChecked);
		}
	}

	var map = new ol.Map({
		layers: [osmLayer, catchmentLayer, municipalitiesLayer],
		target: mapContainerId,
		pixelRatio: 1
	});

	var view = map.getView();

	map.on('moveend', function() {
		setMaxResolutionWaringing()
	});

	function setMaxResolutionWaringing(){
		if(currentMaxResolution !== null) {
			var currRes = view.getResolution();
			if(currRes > currentMaxResolution) {
				createMaxResolutionWarning();
			} else {
				clearMapWarning();
			}
		}
	}

	function setDataAvailabiltyWarning(){
		$('#notification-container').text(translator.getVal('map.dataAvailabilityWarning'));
		$('#notification-container').show();
	}

	function createMaxResolutionWarning() {
		$('#notification-container').text(translator.getVal('map.resolutionwarning'));
		$('#notification-container').show();
	}

	function clearMapWarning() {
		$('#notification-container').empty();
		$('#notification-container').hide();
	}

	function resetMapView() {
		view.setZoom(5);
		view.setCenter(ol.proj.transform([500000, 7200000], 'EPSG:3067', 'EPSG:3857'));
		return false;
	}

	// a normal select interaction to handle click
	var featureSelectInteraction = new ol.interaction.Select({
		toggleCondition: ol.events.condition.always,
		style: new ol.style.Style({
			stroke: new ol.style.Stroke({
				color: 'rgba(102, 178, 255, 1.0)',
				width: 3
			})
		})
	});

	featureSelectInteraction.on('select', function(e) {
		setInfoContent('download');
	});

	var selectedFeatures = featureSelectInteraction.getFeatures();

	selectedFeatures.on('add', function(e) {
		var maxFeatures = getMaxDownloadableFeatureAmount();
		if(selectedFeatures.getLength() > maxFeatures) {
			selectedFeatures.remove(e.element);
			alert(translator.getVal("info.maxfeaturewarning").replace('!maxFeatures!', maxFeatures));
		} else {
			fileLabelList.push(e.element.get('label'));
		}
	});

	selectedFeatures.on('remove', function(e) {
		var deleteIdx = fileLabelList.indexOf(e.element.get('label'));
		if(deleteIdx > -1) {
			fileLabelList.splice(deleteIdx, 1);
		}
	});

	function clearMapFeatureSelection() {
		selectedFeatures.clear();
		setInfoContent('download');
		return false;
	}

	function getMaxDownloadableFeatureAmount() {
		var fileSize = getCurrentLayerData("file_size")
		return fileSize !== null ? Math.floor(MAX_DOWNLOADABLE_SIZE / getCurrentLayerData("file_size")) : 0;
	}

	function getTotalDownloadSize() {
		var fileSize = getCurrentLayerData("file_size")
		return fileSize !== null ? Math.ceil(getCurrentLayerData("file_size") * selectedFeatures.getLength()) : 0;
	}

	map.addInteraction(featureSelectInteraction);

	// a DragBox interaction used to select features by drawing boxes
	var mapDragBox = new ol.interaction.DragBox({
		//    condition: ol.events.condition.altKeyOnly,
		style: new ol.style.Style({
			stroke: new ol.style.Stroke({
				color: [0, 0, 255, 1]
			})
		})
	});

	mapDragBox.on('boxend', function(e) {
		var extent = mapDragBox.getGeometry().getExtent();

		//Check which mapsheets were selected before and which are new mapsheets
		var newFeatures = [];
		var oldFeaturesInSelection = [];
		var existing;
		currentIndexMapLayer.getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
			existing = selectedFeatures.remove(feature);
			if (existing) {
				oldFeaturesInSelection.push(feature);
			} else {
				newFeatures.push(feature);
			}
		});
		//If any of the selected map sheets was new, select all selected mapsheets
		//If all selected mapsheets were selected before leave them unselected
		if (newFeatures.length > 0) {
			selectedFeatures.extend(oldFeaturesInSelection);
			var maxFeatureAmount = getMaxDownloadableFeatureAmount();
			if(selectedFeatures.getLength() + newFeatures.length > maxFeatureAmount) {
				alert(translator.getVal("info.maxfeaturewarning").replace('!maxFeatures!', getMaxDownloadableFeatureAmount()));
			} else {
				selectedFeatures.extend(newFeatures);
			}
		}
		setInfoContent('download');
	});
	map.addInteraction(mapDragBox);

	function getFeatureInfo(evt) {
		setInfoContent("featureinfo", evt);
	}

	var getFeatureInfoToolKey = null;

	//Select right tool
	//Set default
	var dragPan;
	map.getInteractions().forEach(function(interaction) {
		if (interaction instanceof ol.interaction.DragPan) {
			dragPan = interaction;
		}
	}, this);

	//Set interactions based on selection
	function selectPanTool(){
		$('#panselection-button').addClass('active');
		$('#selectselection-button').removeClass('active');
		$('#infoselection-button').removeClass('active');

		selectedTool = "drag";
		dragPan.setActive(true);
		featureSelectInteraction.setActive(false);
		mapDragBox.setActive(false);
		ol.Observable.unByKey(getFeatureInfoToolKey);
	}

	function selectSelectTool(){
		selectTab(0);
		$('#panselection-button').removeClass('active');
		$('#selectselection-button').addClass('active');
		$('#infoselection-button').removeClass('active');

		selectedTool = "select";
		dragPan.setActive(false);
		featureSelectInteraction.setActive(true);
		mapDragBox.setActive(true);
		ol.Observable.unByKey(getFeatureInfoToolKey);
	}

	function selectInfoTool(){
		selectTab(1);
		$('#panselection-button').removeClass('active');
		$('#selectselection-button').removeClass('active');
		$('#infoselection-button').addClass('active');

		selectedTool = "info";
		featureSelectInteraction.setActive(false);
		mapDragBox.setActive(false);
		if (selectedTool != "drag"){
			dragPan.setActive(true);
		}
		getFeatureInfoToolKey = map.on('singleclick',  function(evt){
			getFeatureInfo(evt);
		});
	}

	resetMapView();

	var overviewMap = new ol.control.OverviewMap({
		collapsed: false,
		layers: [osmLayer]
	});

	var layerSwitcher = new ol.control.LayerSwitcher({
		tipLabel: 'Toggle layers' // Optional label for button
	});

	var scaleLineControl = new ol.control.ScaleLine();

	function initLocationSearch() {
		locationSearchInput.keypress(function(event) {
			var keyCode = event.keyCode || event.charCode;
			if(keyCode == 13) {
				var searchStr = locationSearchInput.val();
				if(searchStr.length > 0) {
					var queryUrl = NOMINATIM_API_URL.replace('!query!', searchStr);
					$.getJSON(queryUrl, function(data) {
						if(data.length > 0) {
							map.getView().setCenter(ol.proj.transform([Number(data[0].lon), Number(data[0].lat)], 'EPSG:4326', 'EPSG:3857'));
							if (searchStr.indexOf(",") != -1){
								map.getView().setZoom(16);
							} else {
								map.getView().setZoom(13);
							}
						} else {
							alert(translator.getVal("map.locationNotFound"));
						}
					});
				}
			}
		});
	}

	$('#resetview-button').on('click', resetMapView);
	$('#panselection-button').on('click', selectPanTool);
	$('#selectselection-button').on('click', selectSelectTool);
	$('#clearselection-button').on('click', clearMapFeatureSelection);
	$('#infoselection-button').on('click', selectInfoTool);

	selectPanTool();
	map.addControl(overviewMap);
	map.addControl(layerSwitcher);
	map.addControl(scaleLineControl);
	flashLayerPanel(3000);

	initFormInputs('form-input-container');
	initLocationSearch();
}

checkAccessRights();
checkParameterDatasetAccess();
