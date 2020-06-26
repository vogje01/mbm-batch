import React from "react";
import Accordion from "devextreme-react/accordion";
/*import StepExecutionDetailsAuditing from "./step-execution-details-auditing";
import StepExecutionParamList from "./step-execution-param-list";
import StepExecutionLogList from "./step-execution-log-list";
import StepExecutionDetailsTiming from "./step-execution-details-timing";
import StepExecutionDetailsSteps from "./step-execution-details-steps";*/
import StepExecutionDetailsMain from "./step-execution-details-main";
import StepExecutionDetailsTiming from "./step-execution-details-timing";
import StepExecutionDetailsCounter from "./step-execution-details-counter";
import StepExecutionDetailsAuditing from "./step-execution-details-auditing";
import StepExecutionLogList from "./step-execution-log-list";

class StepExecutionDetailsPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentStepExecution: props.data
        };
        this.customItem = this.customItem.bind(this);
        this.customTitle = this.customTitle.bind(this);
        this.pages = [
            {title: 'Main', item: 'Main'},
            {title: 'Counter', item: 'Counter'},
            {title: 'Timing', item: 'Timing'},
            /* {title: 'Step Executions', item: 'StepExecutionList'},
             {title: 'Parameter', item: 'StepExecutionParameter'},*/
            {title: 'Logs', item: 'StepExecutionLogs'},
            {title: 'Auditing', item: 'Auditing'}
        ];
    }

    customTitle(data) {
        return (
            <span>{data.title}</span>
        );
    }

    customItem(data) {
        switch (data.item) {
            case 'Main':
                return (<StepExecutionDetailsMain currentStepExecution={this.state.currentStepExecution.data}/>);
            case 'Timing':
                return (<StepExecutionDetailsTiming currentStepExecution={this.state.currentStepExecution.data}/>)
            case 'Counter':
                return (<StepExecutionDetailsCounter currentStepExecution={this.state.currentStepExecution.data}/>)
            /*case 'StepExecutionList':
                return (<StepExecutionDetailsSteps currentStepExecution={this.state.currentStepExecution.data}/>);
            case 'StepExecutionParameter':
                return (<StepExecutionParamList currentStepExecution={this.state.currentStepExecution.data}/>);*/
            case 'StepExecutionLogs':
                return (<StepExecutionLogList currentStepExecution={this.state.currentStepExecution.data}/>);
            case 'Auditing':
                return (<StepExecutionDetailsAuditing currentStepExecution={this.state.currentStepExecution.data}/>)
            default:
                return null;
        }
    }

    render() {
        return (
            <React.Fragment>
                <Accordion
                    dataSource={this.pages}
                    collapsible={true}
                    itemTitleRender={this.customTitle}
                    itemRender={this.customItem}>
                </Accordion>
            </React.Fragment>
        );
    }
}

export default StepExecutionDetailsPage