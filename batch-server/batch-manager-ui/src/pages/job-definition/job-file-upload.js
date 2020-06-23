import React from 'react';
import {FileUploader} from "devextreme-react";

class JobFileUpload extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedFiles: []
        };
    }

    onUploaded() {
        console.log("File uploaded");
    }

    render() {
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Job Definition Import</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'}>
                        <FileUploader multiple={false} accept={'application/octet-stream'} uploadMode={'instantly'}
                                      uploadHeaders={{'Authorization': 'Bearer ' + localStorage.getItem('webToken')}}
                                      uploadUrl={process.env.REACT_MANAGER_API_URL + 'jobfileupload'} onUploaded={this.onUploaded.bind(this)}
                                      labelText={''} allowedFileExtensions={['.jar']} maxFileSize={100000000}/>
                        <div className="content" style={{display: this.state.selectedFiles.length > 0 ? 'block' : 'none'}}>
                            <div>
                                <h4>Selected Files</h4>
                                {
                                    this.state.selectedFiles.map((file, i) => {
                                        return <div className="selected-item" key={i}>
                                            <span>{`Name: ${file.name}`}<br/></span>
                                            <span>{`Size ${file.size}`}<br/></span>
                                            <span>{`Type ${file.size}`}<br/></span>
                                            <span>{`Last Modified Date: ${file.lastModifiedDate}`}</span>
                                        </div>;
                                    })
                                }
                            </div>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default JobFileUpload;