import React from "react";
import TextArea from "devextreme-react/text-area";

class JobExecutionDetailsContext extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecution: props.currentJobExecution
        };
    }

    render() {
        return (
            <React.Fragment>
                <TextArea
                    height={120}
                    value={this.state.currentJobExecution.jobExecutionContextDto ? this.state.currentJobExecution.jobExecutionContextDto.serializedContext : null}/>
            </React.Fragment>
        );
    }
}

export default JobExecutionDetailsContext