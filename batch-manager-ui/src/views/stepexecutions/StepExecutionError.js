import React from 'react';
import {TextArea} from "devextreme-react";

class StepExecutionError extends React.Component {

    render() {
        if (this.props.hidden) {
            return null;
        }
        const stepExecutionInfo = this.props.data;
        return (
            <div>
                <div className={'content dx-fieldset'}>
                    <div className={'dx-field'}>
                        <div className={'dx-field-label'}>Error code:</div>
                        <div
                            className={'dx-field-value'}>{stepExecutionInfo.exitStatusInfo ? stepExecutionInfo.exitStatusInfo.exitCode : null}</div>
                    </div>
                </div>
                <TextArea readOnly={true} height={600}
                          value={stepExecutionInfo.exitStatusInfo ? stepExecutionInfo.exitStatusInfo.exitDescription : 'No errors.'}/>
            </div>
        );
    }
}

export default StepExecutionError;
