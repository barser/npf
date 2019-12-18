Ext.application({
    name: 'NpfPocard',
    extend: 'Ext.app.Application',
    requires: [
        'NpfPocard.view.MainView'
    ],
    appFolder: 'js/app',
    mainView: 'NpfPocard.view.MainView'
});