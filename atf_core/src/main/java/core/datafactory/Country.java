package core.datafactory;

import java.util.ArrayList;

public class Country {
	private String countryCode;
	private String countryName;
	private String ISO3166;
	private String ISO3166a3;

	public Country(String countryCode, String countryName, String ISO3166, String ISO3166a3) {
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.ISO3166 = ISO3166;
		this.ISO3166a3 = ISO3166a3;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getISO3166() {
		return ISO3166;
	}

	public String getISO3166a3() {
		return ISO3166a3;
	}

	public static class Countries {
		private static ArrayList<Country> countries = new ArrayList<Country>();

		public static ArrayList<Country> list() {
			if (countries.isEmpty()) {
				generate();
			}
			return countries;
		}

		private static void generate() {
			countries.add(new Country("ad", "Andorra", "020", "AND"));
			countries.add(new Country("ae", "United Arab Emirates", "784", "ARE"));
			countries.add(new Country("af", "Afghanistan", "004", "AFG"));
			countries.add(new Country("ag", "Antigua/Barbuda", "028", "ATG"));
			countries.add(new Country("ai", "Anguilla", "660", "AIA"));
			countries.add(new Country("al", "Albania", "008", "ALB"));
			countries.add(new Country("am", "Armenia", "051", "ARM"));
			countries.add(new Country("an", "Netherlands Antilles", "530", "ANT"));
			countries.add(new Country("ao", "Angola", "024", "AGO"));
			countries.add(new Country("aq", "Antarctica", "010", "ATA"));
			countries.add(new Country("ar", "Argentina", "032", "ARG"));
			countries.add(new Country("as", "American Samoa", "016", "ASM"));
			countries.add(new Country("at", "Austria", "040", "AUT"));
			countries.add(new Country("au", "Australia", "036", "AUS"));
			countries.add(new Country("aw", "Aruba", "533", "ABW"));
			countries.add(new Country("az", "Azerbaidjan", "031", "AZE"));
			countries.add(new Country("ba", "Bosnia-Herzegovina", "070", "BIH"));
			countries.add(new Country("bb", "Barbados", "052", "BRB"));
			countries.add(new Country("bd", "Bangladesh", "050", "BGD"));
			countries.add(new Country("be", "Belgium", "056", "BEL"));
			countries.add(new Country("bf", "Burkina Faso", "854", "BFA"));
			countries.add(new Country("bg", "Bulgaria", "100", "BGR"));
			countries.add(new Country("bh", "Bahrain", "048", "BHR"));
			countries.add(new Country("bi", "Burundi", "108", "BDI"));
			countries.add(new Country("bj", "Benin", "204", "BEN"));
			countries.add(new Country("bm", "Bermuda", "060", "BMU"));
			countries.add(new Country("bn", "Brunei Darussalam", "096", "BRN"));
			countries.add(new Country("bo", "Bolivia", "068", "BOL"));
			countries.add(new Country("br", "Brazil", "076", "BRA"));
			countries.add(new Country("bs", "Bahamas", "044", "BHS"));
			countries.add(new Country("bt", "Bhutan", "064", "BTN"));
			countries.add(new Country("bv", "Bouvet Island", "074", "BVT"));
			countries.add(new Country("bw", "Botswana", "072", "BWA"));
			countries.add(new Country("by", "Belarus", "112", "BLR"));
			countries.add(new Country("bz", "Belize", "084", "BLZ"));
			countries.add(new Country("ca", "Canada", "124", "CAN"));
			countries.add(new Country("cc", "Cocos (Keeling) Isl.", "166", "CCK"));
			countries.add(new Country("cf", "Central African Rep.", "140", "CAF"));
			countries.add(new Country("cg", "Congo", "178", "COG"));
			countries.add(new Country("ch", "Switzerland", "756", "CHE"));
			countries.add(new Country("ci", "Ivory Coast", "384", "CIV"));
			countries.add(new Country("ck", "Cook Islands", "184", "COK"));
			countries.add(new Country("cl", "Chile", "152", "CHL"));
			countries.add(new Country("cm", "Cameroon", "120", "CMR"));
			countries.add(new Country("cn", "China", "156", "CHN"));
			countries.add(new Country("co", "Colombia", "170", "COL"));
			countries.add(new Country("cr", "Costa Rica", "188", "CRI"));
			countries.add(new Country("cs", "Former Czechoslovakia", null, null));
			countries.add(new Country("cu", "Cuba", "192", "CUB"));
			countries.add(new Country("cv", "Cape Verde", "132", "CPV"));
			countries.add(new Country("cx", "Christmas Island", "162", "CXR"));
			countries.add(new Country("cy", "Cyprus", "196", "CYP"));
			countries.add(new Country("cz", "Czech Republic", "203", "CZE"));
			countries.add(new Country("de", "Germany", "276", "DEU"));
			countries.add(new Country("dj", "Djibouti", "262", "DJI"));
			countries.add(new Country("dk", "Denmark", "208", "DNK"));
			countries.add(new Country("dm", "Dominica", "212", "DMA"));
			countries.add(new Country("do", "Dominican Republic", "214", "DOM"));
			countries.add(new Country("dz", "Algeria", "012", "DZA"));
			countries.add(new Country("ec", "Ecuador", "218", "ECU"));
			countries.add(new Country("ee", "Estonia", "233", "EST"));
			countries.add(new Country("eg", "Egypt", "818", "EGY"));
			countries.add(new Country("eh", "Western Sahara", "732", "ESH"));
			countries.add(new Country("er", "Eritrea", "232", "ERI"));
			countries.add(new Country("es", "Spain", "724", "ESP"));
			countries.add(new Country("et", "Ethiopia", "231", "ETH"));
			countries.add(new Country("fi", "Finland", "246", "FIN"));
			countries.add(new Country("fj", "Fiji", "242", "FJI"));
			countries.add(new Country("fk", "Falkland Islands", "238", "FLK"));
			countries.add(new Country("fm", "Micronesia", "583", "FSM"));
			countries.add(new Country("fo", "Faroe Islands", "234", "FRO"));
			countries.add(new Country("fr", "France", "250", "FRA"));
			countries.add(new Country("ga", "Gabon", "266", "GAB"));
			countries.add(new Country("gb", "Great Britain", "826", "GBR"));
			countries.add(new Country("gd", "Grenada", "308", "GRD"));
			countries.add(new Country("ge", "Georgia", "268", "GEO"));
			countries.add(new Country("gf", "French Guyana", "254", "GUF"));
			countries.add(new Country("gh", "Ghana", "288", "GHA"));
			countries.add(new Country("gi", "Gibraltar", "292", "GIB"));
			countries.add(new Country("gl", "Greenland", "304", "GRL"));
			countries.add(new Country("gm", "Gambia", "270", "GMB"));
			countries.add(new Country("gn", "Guinea", "324", "GIN"));
			countries.add(new Country("gp", "Guadeloupe (Fr)", "312", "GLP"));
			countries.add(new Country("gq", "Equatorial Guinea", "226", "GNQ"));
			countries.add(new Country("gr", "Greece", "300", "GRC"));
			countries.add(new Country("gt", "Guatemala", "320", "GTM"));
			countries.add(new Country("gu", "Guam (USA)", "316", "GUM"));
			countries.add(new Country("gw", "Guinea Bissau", "624", "GNB"));
			countries.add(new Country("gy", "Guyana", "328", "GUY"));
			countries.add(new Country("hk", "Hong Kong", "344", "HKG"));
			countries.add(new Country("hn", "Honduras", "340", "HND"));
			countries.add(new Country("hr", "Croatia", "191", "HRV"));
			countries.add(new Country("ht", "Haiti", "332", "HTI"));
			countries.add(new Country("hu", "Hungary", "348", "HUN"));
			countries.add(new Country("id", "Indonesia", "360", "IDN"));
			countries.add(new Country("ie", "Ireland", "372", "IRL"));
			countries.add(new Country("il", "Israel", "376", "ISR"));
			countries.add(new Country("in", "India", "356", "IND"));
			countries.add(new Country("iq", "Iraq", "368", "IRQ"));
			countries.add(new Country("ir", "Iran", "364", "IRN"));
			countries.add(new Country("is", "Iceland", "352", "ISL"));
			countries.add(new Country("it", "Italy", "380", "ITA"));
			countries.add(new Country("jm", "Jamaica", "388", "JAM"));
			countries.add(new Country("jo", "Jordan", "400", "JOR"));
			countries.add(new Country("jp", "Japan", "392", "JPN"));
			countries.add(new Country("ke", "Kenya", "404", "KEN"));
			countries.add(new Country("kg", "Kyrgyzstan", "417", "KGZ"));
			countries.add(new Country("kh", "Cambodia", "116", "KHM"));
			countries.add(new Country("ki", "Kiribati", "296", "KIR"));
			countries.add(new Country("km", "Comoros", "174", "COM"));
			countries.add(new Country("kn", "St Kitts & Nevis", "659", "KNA"));
			countries.add(new Country("kp", "North Korea", "408", "PRK"));
			countries.add(new Country("kr", "South Korea", "410", "KOR"));
			countries.add(new Country("kw", "Kuwait", "414", "KWT"));
			countries.add(new Country("ky", "Cayman Islands", "136", "CYM"));
			countries.add(new Country("kz", "Kazakhstan", "398", "KAZ"));
			countries.add(new Country("la", "Laos", "418", "LAO"));
			countries.add(new Country("lb", "Lebanon", "422", "LBN"));
			countries.add(new Country("lc", "St Lucia", "662", "LCA"));
			countries.add(new Country("li", "Liechtenstein", "438", "LIE"));
			countries.add(new Country("lk", "Sri Lanka", "144", "LKA"));
			countries.add(new Country("lr", "Liberia", "430", "LBR"));
			countries.add(new Country("ls", "Lesotho", "426", "LSO"));
			countries.add(new Country("lt", "Lithuania", "440", "LTU"));
			countries.add(new Country("lu", "Luxembourg", "442", "LUX"));
			countries.add(new Country("lv", "Latvia", "428", "LVA"));
			countries.add(new Country("ly", "Libya", "434", "LBY"));
			countries.add(new Country("ma", "Morocco", "504", "MAR"));
			countries.add(new Country("mc", "Monaco", "492", "MCO"));
			countries.add(new Country("md", "Moldova", "498", "MDA"));
			countries.add(new Country("mg", "Madagascar", "450", "MDG"));
			countries.add(new Country("mh", "Marshall Isl.", "584", "MHL"));
			countries.add(new Country("mk", "Macedonia", "807", "MKD"));
			countries.add(new Country("ml", "Mali", "466", "MLI"));
			countries.add(new Country("mm", "Myanmar", "104", "MMR"));
			countries.add(new Country("mn", "Mongolia", "496", "MNG"));
			countries.add(new Country("mo", "Macau", "446", "MAC"));
			countries.add(new Country("mp", "Northern Mariana Is", "580", "MNP"));
			countries.add(new Country("mq", "Martinique (Fr)", "474", "MTQ"));
			countries.add(new Country("mr", "Mauritania", "478", "MRT"));
			countries.add(new Country("ms", "Montserrat", "500", "MSR"));
			countries.add(new Country("mt", "Malta", "470", "MLT"));
			countries.add(new Country("mu", "Mauritius", "480", "MUS"));
			countries.add(new Country("mv", "Maldives", "462", "MDV"));
			countries.add(new Country("mw", "Malawi", "454", "MWI"));
			countries.add(new Country("mx", "Mexico", "484", "MEX"));
			countries.add(new Country("my", "Malaysia", "458", "MYS"));
			countries.add(new Country("mz", "Mozambique", "508", "MOZ"));
			countries.add(new Country("na", "Namibia", "516", "NAM"));
			countries.add(new Country("nc", "New Caledonia (Fr)", "540", "NCL"));
			countries.add(new Country("ne", "Niger", "562", "NER"));
			countries.add(new Country("nf", "Norfolk Island", "574", "NFK"));
			countries.add(new Country("ng", "Nigeria", "566", "NGA"));
			countries.add(new Country("ni", "Nicaragua", "558", "NIC"));
			countries.add(new Country("nl", "Netherlands", "528", "NLD"));
			countries.add(new Country("no", "Norway", "578", "NOR"));
			countries.add(new Country("np", "Nepal", "524", "NPL"));
			countries.add(new Country("nr", "Nauru", "520", "NRU"));
			countries.add(new Country("nt", "Neutral Zone", null, null));
			countries.add(new Country("nu", "Niue", "570", "NIU"));
			countries.add(new Country("nz", "New Zealand", "554", "NZL"));
			countries.add(new Country("om", "Oman", "512", "OMN"));
			countries.add(new Country("ot", "----Other----", null, null));
			countries.add(new Country("pa", "Panama", "591", "PAN"));
			countries.add(new Country("pe", "Peru", "604", "PER"));
			countries.add(new Country("pf", "Polynesia (French)", "258", "PYF"));
			countries.add(new Country("pg", "Papua New Guinea", "598", "PNG"));
			countries.add(new Country("ph", "Philippines", "608", "PHL"));
			countries.add(new Country("pk", "Pakistan", "586", "PAK"));
			countries.add(new Country("pl", "Poland", "616", "POL"));
			countries.add(new Country("pm", "St Pierre/Miquelon", "666", "SPM"));
			countries.add(new Country("pn", "Pitcairn Island", "612", "PCN"));
			countries.add(new Country("pr", "Puerto Rico", "630", "PRI"));
			countries.add(new Country("pt", "Portugal", "620", "PRT"));
			countries.add(new Country("pw", "Palau", "585", "PLW"));
			countries.add(new Country("py", "Paraguay", "600", "PRY"));
			countries.add(new Country("qa", "Qatar", "634", "QAT"));
			countries.add(new Country("re", "Reunion (French)", "638", "REU"));
			countries.add(new Country("ro", "Romania", "642", "ROU"));
			countries.add(new Country("ru", "Russian Federation", "643", "RUS"));
			countries.add(new Country("rw", "Rwanda", "646", "RWA"));
			countries.add(new Country("sa", "Saudi Arabia", "682", "SAU"));
			countries.add(new Country("sb", "Solomon Islands", "090", "SLB"));
			countries.add(new Country("sc", "Seychelles", "690", "SYC"));
			countries.add(new Country("sd", "Sudan", "736", "SDN"));
			countries.add(new Country("se", "Sweden", "752", "SWE"));
			countries.add(new Country("sg", "Singapore", "702", "SGP"));
			countries.add(new Country("sh", "Saint Helena", "654", "SHN"));
			countries.add(new Country("si", "Slovenia", "705", "SVN"));
			countries.add(new Country("sk", "Slovak Republic", "703", "SVK"));
			countries.add(new Country("sl", "Sierra Leone", "694", "SLE"));
			countries.add(new Country("sm", "San Marino", "674", "SMR"));
			countries.add(new Country("sn", "Senegal", "686", "SEN"));
			countries.add(new Country("so", "Somalia", "706", "SOM"));
			countries.add(new Country("sr", "Suriname", "740", "SUR"));
			countries.add(new Country("st", "St Tome/Principe", "678", "STP"));
			countries.add(new Country("su", "Former USSR", null, null));
			countries.add(new Country("sv", "El Salvador", "222", "SLV"));
			countries.add(new Country("sy", "Syria", "760", "SYR"));
			countries.add(new Country("sz", "Swaziland", "748", "SWZ"));
			countries.add(new Country("tc", "Turks and Caicos", "796", "TCA"));
			countries.add(new Country("td", "Chad", "148", "TCD"));
			countries.add(new Country("tg", "Togo", "768", "TGO"));
			countries.add(new Country("th", "Thailand", "764", "THA"));
			countries.add(new Country("tj", "Tadjikistan", "762", "TJK"));
			countries.add(new Country("tk", "Tokelau", "772", "TKL"));
			countries.add(new Country("tm", "Turkmenistan", "795", "TKM"));
			countries.add(new Country("tn", "Tunisia", "788", "TUN"));
			countries.add(new Country("to", "Tonga", "776", "TON"));
			countries.add(new Country("tp", "East Timor", "626", "TLS"));
			countries.add(new Country("tr", "Turkey", "792", "TUR"));
			countries.add(new Country("tt", "Trinidad/Tobago", "780", "TTO"));
			countries.add(new Country("tv", "Tuvalu", "798", "TUV"));
			countries.add(new Country("tw", "Taiwan", "158", "TWN"));
			countries.add(new Country("tz", "Tanzania", "834", "TZA"));
			countries.add(new Country("ua", "Ukraine", "804", "UKR"));
			countries.add(new Country("ug", "Uganda", "800", "UGA"));
			countries.add(new Country("uk", "United Kingdom", "826", "GBR"));
			countries.add(new Country("um", "USA Minor Islands", "581", "UMI"));
			countries.add(new Country("us", "United States", "840", "USA"));
			countries.add(new Country("uy", "Uruguay", "858", "URY"));
			countries.add(new Country("uz", "Uzbekistan", "860", "UZB"));
			countries.add(new Country("va", "Vatican City State", "336", "VAT"));
			countries.add(new Country("vc", "St Vincent/Grenadines", "670", "VCT"));
			countries.add(new Country("ve", "Venezuela", "862", "VEN"));
			countries.add(new Country("vg", "Virgin Isl. (UK)", "092", "VGB"));
			countries.add(new Country("vi", "Virgin Isl. (USA)", "850", "VIR"));
			countries.add(new Country("vn", "Vietnam", "704", "VNM"));
			countries.add(new Country("vu", "Vanuatu", "548", "VUT"));
			countries.add(new Country("wf", "Wallis/Futuna", "876", "WLF"));
			countries.add(new Country("ws", "Samoa", "882", "WSM"));
			countries.add(new Country("ye", "Yemen", "887", "YEM"));
			countries.add(new Country("yt", "Mayotte", "175", "MYT"));
			countries.add(new Country("yu", "Yugoslavia", "891", "SCG"));
			countries.add(new Country("za", "South Africa", "710", "ZAF"));
			countries.add(new Country("zm", "Zambia", "894", "ZMB"));
			countries.add(new Country("zr", "Zaire", null, null));
			countries.add(new Country("zw", "Zimbabwe", "716", "ZWE"));
		}
		
		public static boolean isValidCountry(String country) {
			for (Country c : Countries.list()) {
				if (c.getCountryName().equals(country)) {
					return true;
				}
			}
			
			return false;
		}
	}
}