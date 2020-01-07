CKEDITOR.plugins.add( 'simage',
{   
   requires : ['richcombo'], //, 'styles' ],
   init : function( editor )
   {
	
     var config = editor.config,
     lang = editor.lang.format;

	 // Gets the list of tags from the settings.
      var tags = []; //new Array();
      //this.add('value', 'drop_text', 'drop_label');
      tags[0]=["<img src='Junit_Image_1574424525876_updated'>", "Junit_Image_1574424525876_updated", "Junit_Image_1574424525876_updated"];	        
    
      // Create style objects for all defined styles.

      editor.ui.addRichCombo( 'simage',
         {
            label : "Select Image",
            title :"Select Image",
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
