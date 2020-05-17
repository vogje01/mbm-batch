import React from 'react';
import Tabs from "devextreme-react/tabs";
import JobExecutionMain from "./JobExecutionMain";
import JobExecutionParams from "./JobExecutionParams";
import JobExecutionLog from "./JobExecutionLog";
import {Popup} from "devextreme-react/popup";
import {Button} from "devextreme-react";

class JobExecutionDetails extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecution: props.currentJobExecution,
            selectedIndex: 0
        };
        this.onTabSelectionChanged = this.onTabSelectionChanged.bind(this);
        this.tabs = [
            {id: 0, text: 'Main', icon: 'material-icons-outlined ic-home md-18', content: 'Main'},
            {id: 1, text: 'Parameter', icon: 'material-icons-outlined ic-list md-18', content: 'Parameter content'},
            {id: 2, text: 'Logs', icon: 'material-icons-outlined ic-list md-18', content: 'Log content'}
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
                    id={'jobExecutionDetailPopup'}
                    className={'popup'}
                    visible={true}
                    dragEnabled={true}
                    closeOnOutsideClick={true}
                    showTitle={true}
                    title={'Job Execution Details: ' + (this.props.currentJobExecution.jobName)}
                    height={'auto'}
                    minWidth={500}
                    minHeight={480}
                    maxWidth={1200}
                    maxHeight={1200}
                    resizeEnabled={true}
                    onHiding={this.props.closePopup}>
                    <Tabs
                        dataSource={this.tabs}
                        selectedIndex={this.state.selectedIndex}
                        onOptionChanged={this.onTabSelectionChanged}/>
                    <div>
                        <JobExecutionMain data={this.props.currentJobExecution}
                                          hidden={this.state.selectedIndex !== 0}/>
                        <JobExecutionParams data={this.props.currentJobExecution}
                                            hidden={this.state.selectedIndex !== 1}/>
                        <JobExecutionLog data={this.props.currentJobExecution}
                                         hidden={this.state.selectedIndex !== 2}/>
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

export default JobExecutionDetails;