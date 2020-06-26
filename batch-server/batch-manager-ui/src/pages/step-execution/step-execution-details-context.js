import React from "react";
import TextArea from "devextreme-react/text-area";

class StepExecutionDetailsContext extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentStepExecution: props.currentStepExecution
        };
    }

    render() {
        return (
            <React.Fragment>
                <TextArea
                    height={120}
                    value={this.state.currentStepExecution.stepExecutionContextDto ? this.state.currentStepExecution.stepExecutionContextDto.serializedContext : null}/>
            </React.Fragment>
        );
    }
}

export default StepExecutionDetailsContext