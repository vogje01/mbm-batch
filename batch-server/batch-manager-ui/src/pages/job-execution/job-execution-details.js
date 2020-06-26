import React from "react";
import Accordion from "devextreme-react/accordion";
import StepExecutionList from "../step-execution/step-execution-list";
import JobExecutionDetailsAuditing from "./job-execution-details-auditing";
import JobExecutionParamList from "./job-execution-param-list";
import JobExecutionLogList from "./job-execution-log-list";
import JobExecutionDetailsTiming from "./job-execution-details-timing";
import JobExecutionDetailsSteps from "./job-execution-details-steps";

function CustomTitle(data) {
    return (
        <span>{data.title}</span>
    );
}

class JobExecutionDetailsPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecution: props.data
        };
        this.customItem = this.customItem.bind(this);
        this.pages = [
            {title: 'Timing', item: 'Timing'},
            {title: 'Step Executions', item: 'StepExecutionList'},
            {title: 'Parameter', item: 'JobExecutionParameter'},
            {title: 'Logs', item: 'JobExecutionLogs'},
            {title: 'Auditing', item: 'Auditing'}
        ];
    }

    customItem(data) {
        switch (data.item) {
            case 'StepExecutionList':
                return (<JobExecutionDetailsSteps currentJobExecution={this.state.currentJobExecution.data}/>);
            case 'JobExecutionParameter':
                return (<JobExecutionParamList currentJobExecution={this.state.currentJobExecution.data}/>);
            case 'JobExecutionLogs':
                return (<JobExecutionLogList currentJobExecution={this.state.currentJobExecution.data}/>);
            case 'Auditing':
                return (<JobExecutionDetailsAuditing currentJobExecution={this.state.currentJobExecution.data}/>)
            case 'Timing':
                return (<JobExecutionDetailsTiming currentJobExecution={this.state.currentJobExecution.data}/>)
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
                    itemTitleRender={CustomTitle}
                    itemRender={this.customItem}>
                </Accordion>
            </React.Fragment>
        );
    }
}

export default JobExecutionDetailsPage