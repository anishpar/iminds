CKEDITOR.plugins.add( 'message',
{   
   requires : ['richcombo'], //, 'styles' ],
   init : function( editor )
   {
    
	var config = editor.config,
        lang = editor.lang.format;


	 // Gets the list of tags from the settings.
      var tags = []; //new Array();
      //this.add('value', 'drop_text', 'drop_label');
      tags[0]=["${FNF_MEMBER_LIST}", "FNF Member List", "${FNF_MEMBER_LIST}"];
      tags[1]=["${PATTERN}", "Charging Pattern", "${PATTERN}"];
      tags[2]=["${CUSTOMER_PASSWORD}", "Customer Password", "${CUSTOMER_PASSWORD}"];
      tags[3]=["${CUG_GROUP_NAME}", "CUG Group Name", "${CUG_GROUP_NAME}"];
      tags[4]=["${FNF_LIST_NUMBER}", "FNF List Number", "${FNF_LIST_NUMBER}"];
      tags[5]=["${PACKAGE_DESCRIPTION}", "Package Description", "${PACKAGE_DESCRIPTION}"];
      tags[6]=["${CUSTOMER_CATEGORY}", "Customer category", "${CUSTOMER_CATEGORY}"];
      tags[7]=["${ACCOUNT_STATUS_NAME}", "Account Status Name", "${ACCOUNT_STATUS_NAME}"];
      tags[8]=["${SERVICE_PASSWORD}", "Service Password", "${SERVICE_PASSWORD}"];
      tags[9]=["${INVENTORY_NUMBER}", "Inventory Number", "${INVENTORY_NUMBER}"]; 		
 	
      tags[10]=["${CUG_GROUP_MEMBER_NUMBER}", "CUG Group Member Number", "${CUG_GROUP_MEMBER_NUMBER}"];
      tags[11]=["${PACKAGE_EXPIRY}", "Package Expiry", "${PACKAGE_EXPIRY}"];
      tags[12]=["${NEXT_BILL_DATE}", "Account Next Bill Date", "${NEXT_BILL_DATE}"];
      tags[13]=["${CUG_MEMBER_SHORT_CODE}", "CUG Member Short  Code", "${CUG_MEMBER_SHORT_CODE}"];
      tags[14]=["${CUSTOMER_ACCOUNT_NAME}", "Customer Account Name", "${CUSTOMER_ACCOUNT_NAME}"];
      tags[15]=["${PACKAGENAME}", "Package Name", "${PACKAGENAME}"];
      tags[16]=["${CUSTOMER_ACCOUNT_NUMBER}", "Customer Account Number", "${CUSTOMER_ACCOUNT_NUMBER}"];
      tags[17]=["${PACKAGE_SHORT_DESCRIPTION}", "Package Short Description", "${PACKAGE_SHORT_DESCRIPTION}"];
      tags[18]=["${CUSTOMER_USERNAME}", "Customer Username", "${CUSTOMER_USERNAME}"];
      tags[19]=["${SERVICE_USERNAME}", "Service Username", "${SERVICE_USERNAME}"];  	        

      tags[20]=["${CUG_GROUP_NUMBER}", "CUG Group Number", "${CUG_GROUP_NUMBER}"];
      tags[21]=["${BILLING_AREA}", "Billing Area", "${BILLING_AREA}"];
      tags[22]=["${SYSTEM_DATE}", "System Date", "${SYSTEM_DATE}"];
      // Create style objects for all defined styles.

      editor.ui.addRichCombo( 'message',
         {
            label : "Select Message",
            title :"Select Message",	    
            multiSelect : false,

            modes: { wysiwyg: 1, source: 1 },

            panel: {
               css: [ CKEDITOR.skin.getPath( 'editor' ) ].concat( config.contentsCss ),
               multiSelect: false,
               attributes: { 'aria-label': 'Xlated title'}
           },		

	   init : function()
            {
               //this.startGroup( "message" );
               //this.add('value', 'drop_text', 'drop_label');
               for (var this_tag in tags){
                  this.add(tags[this_tag][0], tags[this_tag][1], tags[this_tag][2]);
               }	       

            },

            onClick : function( value )
            {         
               editor.focus();
               editor.fire( 'saveSnapshot' );
               editor.insertHtml(value);
               editor.fire( 'saveSnapshot' );
            }
            
         });
   }
});
