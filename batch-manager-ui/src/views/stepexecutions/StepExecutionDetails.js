import React from 'react';
import Tabs from "devextreme-react/tabs";
import {Popup} from "devextreme-react/popup";
import {Button} from "devextreme-react";
import StepExecutionLog from "./StepExecutionLog";
import StepExecutionMain from "./StepExecutionMain";
import StepExecutionError from "./StepExecutionError";

class StepExecutionDetails extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentStepExecution: props.currentStepExecution,
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
                    id={'stepExecutionDetailPopup'}
                    className={'popup'}
                    visible={true}
                    dragEnabled={true}
                    closeOnOutsideClick={true}
                    showTitle={true}
                    title={'Step Execution Details: ' + (this.props.currentStepExecution.stepName)}
                    height={'auto'}
                    minWidth={500}
                    maxWidth={1200}
                    maxHeight={1200}
                    resizeEnabled={true}
                    onHiding={this.props.closePopup}>
                    <Tabs
                        dataSource={this.tabs}
                        selectedIndex={this.state.selectedIndex}
                        onOptionChanged={this.onTabSelectionChanged}/>
                    <div>
                        <StepExecutionMain data={this.props.currentStepExecution}
                                           hidden={this.state.selectedIndex !== 0}/>
                        <StepExecutionError data={this.props.currentStepExecution}
                                            hidden={this.state.selectedIndex !== 1}/>
                        <StepExecutionLog data={this.props.currentStepExecution}
                                          hidden={this.state.selectedIndex !== 2}/>
                    </div>
                    <Button
                        width={90}
                        text={'Close'}
                        type={'success'}
                        stylingMode={'contained'}
                        onClick={this.props.closePopup}
                        style={{float: 'right', margin: '5px 10px 0 0'}}/>
                </Popup>
            </React.Fragment>
        );
    }
}

export default StepExecutionDetails;