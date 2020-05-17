import {CheckBox} from "devextreme-react";
import React from "react";


function JobDefinitionParamValueTemplate(cell) {
    const {type} = cell.data;
    switch (type) {
        case 'STRING':
            return (<span>{cell.data.stringValue}</span>);
        case 'LONG':
            return (<span>{cell.data.longValue}</span>);
        case 'DOUBLE':
            return (<span>{cell.data.doubleValue}</span>);
        case 'BOOLEAN':
            return (<CheckBox value={cell.data.booleanValue} readOnly={true}/>);
        default:
            return (<span>Enter value</span>);
    }
}


class JobDefinitionValue extends React.Component {

    render() {
        return (JobDefinitionParamValueTemplate(this.props.data));
    }
}

export {JobDefinitionValue}