Ext.define('NpfPocard.view.MainView', {
    extend: 'Ext.container.Viewport',

    requires: [
        'Common.model.Pocard'
    ],

    layout: 'fit',

    items: [
        {
            xtype: 'grid',
            title: 'Список платежных поручений',
            store: {
                model: 'Common.model.Pocard',
                autoLoad: true,
                proxy: {
                    type: 'ajax',
                    timeout: 5 * 60 * 1000,
                    url: '/list',
                    reader: {
                        type: 'json',
                        rootProperty: 'data',
                        totalProperty: 'count'
                    },
                    actionMethods: {
                        read: 'GET'
                    }
                }
            },
            columns: [
                { dataIndex: 'id', header: 'ID'},
                { dataIndex: 'amount', header: 'AMOUNT'},
                { dataIndex: 'comments', header: 'COMMENTS'},
            ]
        }
    ]
});