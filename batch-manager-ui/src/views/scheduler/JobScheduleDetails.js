import React from 'react';
import Tabs from "devextreme-react/tabs";
import {Popup} from "devextreme-react/popup";
import {Button} from "devextreme-react";
import JobScheduleMain from "./JobScheduleMain";
import JobScheduleAgents from "./JobScheduleAgents";

class JobScheduleDetails extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobSchedule: props.currentJobSchedule,
            selectedIndex: 0
        };
        this.onTabSelectionChanged = this.onTabSelectionChanged.bind(this);
        this.tabs = [
            {id: 0, text: 'Main', icon: 'material-icons-outlined ic-home md-18'},
            {id: 1, text: 'Agents', icon: 'material-icons-outlined ic-developer-board md-18'}
        ];
    }

    onTabSelectionChanged(index) {
        if (index.name === 'selectedIndex') {
            this.setState({
                selectedIndex: index.value,
            });
        }
    }

    render() {
        return (
            <React.Fragment>
                <Popup
                    id={'jobScheduleDetailPopup'}
                    className={'popup'}
                    visible={true}
                    dragEnabled={true}
                    closeOnOutsideClick={true}
                    showTitle={true}
                    title={'Job Schedule Details: ' + this.props.currentJobSchedule.name}
                    height={'auto'}
                    minWidth={500}
                    maxWidth={1200}
                    minHeight={100}
                    maxHeight={1200}
                    resizeEnabled={true}
                    onHiding={this.props.closePopup}>
                    <Tabs
                        dataSource={this.tabs}
                        selectedIndex={this.state.selectedIndex}
                        onOptionChanged={this.onTabSelectionChanged}/>
                    <div>
                        <JobScheduleMain data={this.state.currentJobSchedule}
                                         hidden={this.state.selectedIndex !== 0}
                                         closePopup={this.props.closePopup}/>
                        <JobScheduleAgents data={this.state.currentJobSchedule}
                                           hidden={this.state.selectedIndex !== 1}
                                           closePopup={this.props.closePopup}/>
                    </div>
                    <Button
                        width={90}
                        text={'Close'}
                        type={'success'}
                        stylingMode={'contained'}
                        onClick={this.props.closePopup}
                        style={{float: 'right', margin: '5px 10px 10px 0'}}/>
                </Popup>
            </React.Fragment>
        );
    }
}

export default JobScheduleDetails;