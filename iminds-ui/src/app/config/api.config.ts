export const APIConfigurations = {
    'DEV': {
        'DEV.LOGIN': 'assets/json/login_success.json',
        'DEV.LOGIN_USER_DETAIL': 'assets/json/loggein_user_details.json',
        'DEV.LOGOUT': 'assets/json/logout_success.json',
        'DEV.LOAD_MENU': 'assets/json/load_menu.json',
        'DEV.GET_ACCESS': 'assets/json/load_menu_access.json',
        'DEV.CHANGEPW': 'assets/json/common_success.json',
        'DEV.LOG_ERROR' : 'assets/json/error_logged.json',
      'DEV.LOAD_CONFIGURATION': 'assets/json/load_configuration.json',

        'DEV.SAMPLE_API_CONSTANT' : 'assets/json/staticResponse.json',
        'DEV.CHANNEL_MASTER' : 'assets/json/notification/getAllChannel.json',
      'DEV.CREATE_TEMPLATE': 'assets/json/template/create_template.json',
      'DEV.SEARCH_HISTORY': 'assets/json/notification/searchHistory.json',
      'DEV.GET_FILTERED_TEMPLATE':'assets/json/template/get_filtered_template.json',
      'DEV.EVENT_TEMPLATE_CONFIGURATION':'assets/json/notification/create_event_template_Association.json',
      'DEV.GET_EVENT_TAGS':'assets/json/notification/get_event_tags.json',
      'DEV.REPROCESS_NOTIFICATION':'assets/json/notification/reProcessNotification.json',
      'DEV.GET_REMAINING_EVENTS':'assets/json/notification/get_remaining_event.json',
      'DEV.VALIDATE_EXPRESSION':'assets/json/notification/validate_expression.json',
      'DEV.GET_SUPPORTED_ATTRIBUTES':'assets/json/notification/getSupportedAttributes.json',
      'DEV.CHANNEL' :'assets/json/notification/createChannel.json'
      
        // ENDS
    },

    'COMMON': {
        'LOGIN': 'customer/login/',
        'LOGIN_USER_DETAIL': 'common/loggedInUserDetails/',
        'LOGOUT': 'customer/logout/',
        'LOAD_MENU': 'customer/menu/loadUserMenu/',
        'GET_ACCESS': 'customer/menu/loadUserMenuById/',
        'LOG_ERROR': 'uiLog/print/',
        'LOAD_CONFIGURATION': 'loadConfiguration',
        'GET_FILTERED_TEMPLATE' : 'template',
        'EVENT_TEMPLATE_CONFIGURATION' : 'templateRelation',
        'GET_EVENT_TAGS' : 'eventTag',
        'GET_REMAINING_EVENTS':'templateRelation/nonAssociatedEvent/',
        'VALIDATE_EXPRESSION':'templateRelation/validateExpression'

    },

    'NOTIFICATION': {
      'SAMPLE_API_CONSTANT': 'common/actualEndPoint/',
      'CHANNEL_MASTER': 'channel/',
      'CREATE_TEMPLATE': 'template',
      'SEARCH_HISTORY':'communicationMessage',
      'REPROCESS_NOTIFICATION':'communicationMessage/reprocess',
      'GET_SUPPORTED_ATTRIBUTES':'channel/supportedAttribute',
      'CHANNEL': 'channel',
    }
};
