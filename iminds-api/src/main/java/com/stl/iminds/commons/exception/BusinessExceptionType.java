package com.stl.iminds.commons.exception;

import com.stl.core.commons.exception.ExceptionType;

/**
 * The Enum ExceptionType.
 */
public enum BusinessExceptionType implements ExceptionType{

	INVALID_INPUTS(1000L, "invalid.input"), 
	MISSING_MANDATORY_PARAMETERS(1001L, "missing.mandatory"),
	NOT_SUPPORTED(1002L, "not.supported"),
	MISSING_CONFIGURATION(1003L, "missing.configuration"),
	DATA_NOT_FOUND(1004L, "data.not.found"),
	PLUGIN_NOT_FOUND(1005L, "plugin.not.found"),
	CHANNEL_NOT_FOUND(1006L, "channel.not.found"),
	TECHNICAL_FAILURE(9999L, "technical.failure"),
	QUERY_CACHE_NOT_FOUND(1007, "query.cache.not.found"),
	SCHEDULED_TIME_INVALID(1008,"schedule.time.invalid"),
	COMMUNICATION_ADDRESS_INVALID(1009,"address.invalid"),
	UNIQUE_ALIAS(1010L, "channel.alias.unique"),
	TEMPLATE_NOT_FOUND(1011L, "template.not.found"),
	CLASS_NOT_FOUND(1012L, "class.not.found"),
	EVENT_CONFIGURATION_NOT_FOUND(1013L, "event.configuration.not.found"),
	TEMPLATE_CONTENT_NOT_FOUND(1012L, "template.conetent.not.found.for.language"),
	CLASS_NOT_IMPLEMENT(1011L, "class.not.implement"),
	IMAGE_NOT_FOUND(1014L, "image.not.found"),
	FROMDATE_INVALID(1015,"fromDate.invalid"),
	TODATE_INVALID(1016,"toDate.invalid"),
	TRANSID_OR_REQID_INVALID(1017,"transId.or.requestId.or.recipient.mandatory"),
	TODATE_LESSTHEN_FROMDATE(1018,"toDate.lessthen.fromDate"),
	FROMDATE_TODATE_DIFFERENCE(1019,"fromDate.and.toDate.30DaysDiffernece"),
	NOT_FOUND(1020L, "not.found"),
	EVENT_CONFIG_NOT_FOUND(1021L,"event.configuration.not.found"),
	PLUGIN_NOTFOUND(1022L,"plugin.not.found"),
	NOT_FOUND_FOR(1023L,"not.found.for"),

	SPECIAL_CHAR_MSG(1045L,"special_char_not_allowed"),
	MAX_CHAR_ALLOWED(1046L,"max.length.allowed"),
	UNIQUE_NAME(1024L, "name.unique"),
	EVENT_CONFIG_TEMP_REL_MISSING(1024L,"templateRelation.mandatory"),
	EVENT_CONFIG_DEFAULT_TEMP_MORETHAN_ONE(1025L,"defaulttemp.morethanone"),
	EVENT_CONFIG_DEFAULT_TEMP_LOWER_PRIORITY(1026L,"defaulttemp.lower.priority"),
	EVENT_CONFIG_NON_EXISITNG_TEMPLATE(1027L,"nonexisting.template"),
	EVENT_CONFIG_NON_EXISITNG_TEMP_PRIORITY(1028L,"nonexisting.template.priority"),
	EVENT_CONFIG_SEND_TO(1029L,"sendto.mandatory"),
	EVENT_CONFIG_SEND_TO_INVALID(1030L,".sendto.invalid"),
	EVENT_CONFIG_ALIAS_MAN_MISSING(1031L,"mandate.parameter.missing"),
	EVENT_CONFIG_PRIORITY_NOT_SEQUENTIAL(1032L,"priority.not.sequential"),
	EVENT_CONFIG_SEND_TO_TYPE(1033L,"sendto.type"),
	TRANSACTION_ID_NOT_FOUND(1034L,"transaction.id.not.found"),
	INVALID_STATUS(1035L,"invalid.status"),
	INVALID_COMMUNICATION_ADDRESS(1036L,"invalid.communication.address"),
	NO_DATA_FOUND_TO_SEND(1037L,"no.data.found.to.send"),
	INVALID_NOTIFICATION_ATTACHMENTDATA(1038L,"invalid.notification.attachmentdata"),
	EVENT_CONFIG_DEFAULT_WITH_COND(1039L,"default.template.with.condition"),
	EVENT_CONFIG_EXPR_MISSING_COND(1040L,"expr.template.without.condition"),
	EVENT_CONFIG_DUP_TEMPLATE(1041L,"expr.duplicate.template"),
	EVENT_CONFIG_INVALID_COND_EXPR(1042L,"expr.invalid"),
	EVENT_CONFIG_CHANNEL_ALREADY_ASSOCIATED(1043L,"channel.already.associated"),
	EVENT_CONFIG_DOESNOTEXISTS_ASSOCIATION(1044L,"doesnotexists.association"),
	INVALID_INPUT(1045L,"invalid.input"),
	TEMPLATE_DUPLICATE_CONTENT_FOUND(1046L, "template.duplicate.conetent.found.for.language"),
	LANGUAGE_NOT_FOUND(1047L,"language.not.found"),
	TEMPLATE_INVALID_NAME(1048L,"invalid.name"),
	TEMPLATE_INVALID_CHANNEL(1049L,"invalid.channel"),
	TRANSACTION_ID_NOT_UNIQUE(1051L,"transaction.id.not.unique"),
	TEMPLATE_INVALID_EVENT(1050L,"invalid.event"),
	DB_LENGTH_INVALID(1052L,"invalid.dblength");
    private long errorCode;
	private String errorMsgId;

	BusinessExceptionType(long code, String message) {
        this.errorCode = code;
        this.errorMsgId = message;
    }

    /**
     *
     * @return
     */
    public String getMessageMsgId() {
        return errorMsgId;
    }

	public long getErrorCode() {
		return errorCode;
	}

}
     
