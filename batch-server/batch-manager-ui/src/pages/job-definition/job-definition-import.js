import React from 'react';
import {Button, TextArea} from "devextreme-react";
import {errorMessage, infoMessage} from "../../utils/message-util";

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
        fetch(process.env.REACT_APP_API_URL + 'jobdefinitions/import',
            {
                headers: {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem('webToken')},
                method: 'PUT',
                body: JSON.stringify(this.state.currentJobDefinitions)
            })
            .then(response => {
                if (response.status === 200) {
                    this.setState({currentJobDefinitions: [], importPopupVisible: false})
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
                        <TextArea
                            autoResizeEnabled={true}
                            onValueChanged={this.onImportTextAreaChanged}/>
                        <Button
                            width={90}
                            text={'Import'}
                            type={'normal'}
                            stylingMode={'contained'}
                            onClick={this.importJobDefinitions}
                            style={{float: 'right', margin: '5px 10px 0 0'}}/>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default JobDefinitionImport;