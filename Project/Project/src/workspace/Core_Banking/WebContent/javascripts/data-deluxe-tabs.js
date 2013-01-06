/*
   Deluxe Menu Data File
   Created by Deluxe Tuner v3.0
   http://deluxe-menu.com
*/


// -- Deluxe Tuner Style Names
var bstylesNames=["Individual Style",];
// -- End of Deluxe Tuner Style Names

//--- Common
var bblankImage="deluxe-tabs.files/blank.gif";
var bitemCursor="pointer";
var bmenuOrientation=0;
var bselectedItem=1;

//--- Dimensions
var bmenuWidth="970px";
var bmenuHeight="21px";

//--- Positioning
var babsolute=0;
var bleft="120px";
var btop="120px";

//--- Font
var bfontStyle=["normal 10pt Tahoma","",""];
var bfontColor=["#000000","",""];
var bfontDecoration=["none","none","none"];

//--- Tab-mode
var tabMode=1;
var bselectedSmItem=-1;
var bsmHeight=20;
var bsmBackColor="#797979";
var bsmBorderColor="#91A7B4";
var bsmBorderWidth=1;
var bsmBorderStyle="solid";
var bsmBorderBottomDraw=1;
var bitemTarget="_self";
var bsmItemAlign="center";
var bsmItemSpacing=1;
var bsmItemPadding="2px";

//--- Appearance
var bmenuBackColor="";
var bmenuBackImage="";
var bmenuBorderColor="#FFFFFF";
var bmenuBorderWidth=0;
var bmenuBorderStyle="ridge";

//--- Tabs Appearance
var bbeforeItemSpace=0;
var bafterItemSpace=0;
var bitemBackColor=["#ffffff","#FFEEB9","#F9BC00"];
var bitemBorderColor=["","",""];
var bitemBorderWidth=0;
var bitemBorderStyle=["ridge","ridge","ridge"];
var bitemAlign="center";
var bitemSpacing=0;
var bitemPadding="0px";
var browSpace=0;

//--- Tabs Images
var bitemBackImage=["deluxe-tabs.files/tab01_back_n.gif","deluxe-tabs.files/tab01_back_o.gif","deluxe-tabs.files/tab01_back_s.gif"];
var bbeforeItemImage=["deluxe-tabs.files/tab01_before_n.gif","deluxe-tabs.files/tab01_before_o.gif","deluxe-tabs.files/tab01_before_s.gif"];
var bafterItemImage=["deluxe-tabs.files/tab01_after_n.gif","deluxe-tabs.files/tab01_after_o.gif","deluxe-tabs.files/tab01_after_s.gif"];
var bbeforeItemImageW=5;
var bbeforeItemImageH=20;
var bafterItemImageW=5;
var bafterItemImageH=20;

//--- Icons
var biconWidth=16;
var biconHeight=16;
var biconAlign="left";
var texpandBtn=["","",""];
var texpandBtnW=9;
var texpandBtnH=9;
var texpandBtnAlign="left";

//--- Separators
var bseparatorWidth="1px";

//--- Transitional Effects
var btransition=24;
var btransOptions="";
var btransDuration=300;

//--- Floatable Menu
var bfloatable=1;
var bfloatIterations=6;

var bstyles = [
    ["bitemBackImage=","bbeforeItemImage=","bafterItemImage=","bitemBorderColor=#ffffff,#316AC5,#316AC5","bitemBorderWidth=1","bitemBackColor=#ffffff,#bbccee,#bbccee","bitemBorderStyle=solid"],
];

var bmenuItems = [

    ["-","", "", "", "", "", "", "", "", ],
    ["Personal","", "", "", "", "", "", "", "", ],
        ["|Accounts","Account", "", "", "", "", "", "", "", ],
        ["|Loans","Loan", "", "", "", "", "", "", "", ],
        ["|Transactions","Transaction", "", "", "", "", "", "", "", ],
	["|Treasury","Treasury", "", "", "", "", "", "", "", ],
    ["Rural Sector","", "", "", "", "", "", "", "", ],
        ["|Small Scale","SS", "", "", "", "", "", "", "", ],
        ["|Agriculture","Agro", "", "", "", "", "", "", "", ],
        ["|Rural Loans","Rural", "", "", "", "", "", "", "", ],
    ["Remittance","", "", "", "", "", "", "", "", ],
	["|InterBanking","Remit", "", "", "", "", "", "", "", ],
	["|Cheques","Cheque", "", "", "", "", "", "", "", ],
	["|Drafts","Draft", "", "", "", "", "", "", "", ],
    ["Corporate","", "", "", "", "", "", "", "", ],
	["|Corporate","Corporate", "", "", "", "", "", "", "", ],
    ["Services","", "", "", "", "", "", "", "", ],
        ["|Bills","Bill", "", "", "", "", "", "", "", ],
        ["|Guarantees","Guarantee", "", "", "", "", "", "", "", ],
        ["|Taxes","Tax", "", "", "", "", "", "", "", ],
    ["Help","", "", "", "", "", "", "", "", ],
        ["|Help","help/index.htm", "", "", "", "", "", "", "", ],	
    ["-","", "", "", "", "", "", "", "", ],
];

dtabs_init();
