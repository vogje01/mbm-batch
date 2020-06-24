import React from 'react';
import {TextArea} from "devextreme-react";
import {errorMessage, infoMessage} from "../../utils/message-util";
import Toolbar, {Item} from "devextreme-react/toolbar";

class JobDefinitionImport extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinitions: []
        };
    }

    onImportTextAreaChanged(e) {
        this.setState({currentJobDefinitions: JSON.parse(e.value)})
    }

    importJobDefinitions() {
        fetch(process.env.REACT_APP_MANAGER_URL + 'jobdefinitions/import',
            {
                headers: {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem('webToken')},
                method: 'PUT',
                body: JSON.stringify(this.state.currentJobDefinitions)
            })
            .then(response => {
                if (response.status === 200) {
                    this.props.history.push('/jobdefinitions')
                    infoMessage('Job definitions imported');
                }
            })
            .catch((error) => {
                errorMessage('Job definitions import error: ' + error.message);
            });
    }

    render() {
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Job Definition Import</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'}>
                        <Toolbar>
                            <Item
                                location="after"
                                widget="dxButton"
                                options={{text: "Import", stylingMode: "Contained", onClick: this.importJobDefinitions.bind(this)}}/>
                        </Toolbar>
                        <TextArea
                            autoResizeEnabled={true}
                            onValueChanged={this.onImportTextAreaChanged}/>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default JobDefinitionImport;