import React from 'react';
import List from 'devextreme-react/list.js';

const navigation = [
    {id: 1, text: 'General'},
    {id: 2, text: 'Timing'},
    {id: 3, text: 'Parameter'},
    {id: 4, text: 'Logs'},
    {id: 5, text: 'Auditing'}
];

class NavigationList extends React.PureComponent {
    render() {
        return (
            <div className="list">
                <List
                    dataSource={navigation}
                    elementAttr={{class: 'panel-list dx-theme-accent-as-background-color'}}
                    height={200}/>
            </div>
        );
    }
}

export default NavigationList;
