import React from "react";
import FileManager, {Permissions} from "devextreme-react/file-manager";
import CustomFileSystemProvider from "devextreme/file_management/custom_provider";
import {EndTimer, StartTimer} from "../../utils/method-timer";
import {handleResponse, initPut} from "../../utils/server-connection";

export function provider() {
    return new CustomFileSystemProvider({
        getItems: function (pathInfo) {
            StartTimer();
            let url = process.env.REACT_APP_SCHEDULER_URL + 'filesystem/getItems';
            return fetch(url, initPut(JSON.stringify(pathInfo)))
                .then(response => {
                    return handleResponse(response)
                })
                .then(data => {
                    return data
                })
                .finally(() => EndTimer());
        },
        uploadFileChunk: function (fileData, chunksInfo, destinationDir) {
            StartTimer();
            let reader = new FileReader();
            reader.readAsDataURL(chunksInfo.chunkBlob);
            reader.onloadend = function () {
                let uploadDto = {fileName: fileData.name, fileData: reader.result, fileRelativePath: destinationDir.relativeName, fileSize: fileData.size}
                let url = process.env.REACT_APP_SCHEDULER_URL + 'library/upload/chunk';
                return fetch(url, initPut(JSON.stringify(uploadDto)))
                    .then(response => {
                        return handleResponse(response)
                    })
                    .then(data => {
                        return data
                    })
                    .finally(() => EndTimer());
            };
        },
        uploadChunkSize: 10000
    })
}

class JobFileList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        return (
            <FileManager
                fileSystemProvider={provider()}
                currentPath={'.'}>
                <Permissions
                    // create={true}
                    // copy={true}
                    // move={true}
                    // delete={true}
                    // rename={true}
                    upload={true}
                    download={true}>
                </Permissions>
            </FileManager>
        );
    }
}

export default JobFileList
