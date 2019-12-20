Ext.define('Common.model.Pocard', {
    extend: 'Common.model.Base',

    idProperty: 'id',

    fields: [
        {
            name: 'no', type: 'number'
        },
        {
            name: 'date', type: 'string'
        },
        {
            name: 'amount', type: 'number'
        },
        {
            name: 'person_fio', type: 'string'
        },
        {
            name: 'contract_no', type: 'string'
        },
        {
            name: 'comments', type: 'string'
        },
        {
            name: 'state', type: 'string'
        },
        {
            name: 'person_id', type: 'number'
        },
        {
            name: 'contract_id', type: 'number'
        },
        {
            name: 'entry_no_nav', type: 'string'
        }
    ]
});