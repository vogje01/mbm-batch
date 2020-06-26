import Form, {SimpleItem} from "devextreme-react/form";
import React from "react";

class StepExecutionDetailsMain extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentStepExecution: props.currentStepExecution
        };
    }

    render() {
        return (
            <React.Fragment>
                <Form
                    formData={this.state.currentStepExecution}
                    colCount={5}>
                    <SimpleItem dataField="jobName" readOnly={true}/>
                    <SimpleItem dataField="stepName" readOnly={true}/>
                    <SimpleItem dataField="hostName" readOnly={true}/>
                    <SimpleItem dataField="nodeName" readOnly={true}/>
                    <SimpleItem dataField="status" readOnly={true}/>
                    <SimpleItem dataField="stepExecutionId" readOnly={true}/>
                    <SimpleItem dataField="exitCode" readOnly={true}/>
                    <SimpleItem dataField="exitMessage" readOnly={true}/>
                </Form>
            </React.Fragment>
        );
    }
}

export default StepExecutionDetailsMain