import { Modal, Button, Row, Col, Form, Dropdown, InputGroup } from 'react-bootstrap';
import fetch from 'isomorphic-unfetch';
import { saveAs } from 'file-saver';
import axios, { post } from 'axios';
import { Container } from "next/app";
import ModalHeader from 'react-bootstrap/ModalHeader';

class Export extends React.Component {
    constructor() {
        super();
        this.state = {
            file:null
        };
        this.onFormSubmit = this.onFormSubmit.bind(this)
        this.onChange = this.onChange.bind(this)
        this.fileUpload = this.fileUpload.bind(this)
    };

   onFormSubmit(e){
    e.preventDefault() // Stop form submit
    this.fileUpload(this.state.file).then((response)=>{
      console.log(response.data);
    })
  }
  onChange(e) {
    this.setState({file:e.target.files[0]})
  }
  fileUpload(file){
    const url = 'http://localhost:8080/api/vocabulary/Import';
    const formData = new FormData();
    formData.append('file',file)
    const config = {
        headers: {
            'content-type': 'multipart/form-data'
        }
    }
    alert("Successfull")
    console.log("Successfully uploaded");
    return post(url, formData,config)
  }

    //Export and Save as File
    exportVocab() {
        var FileSaver = require('file-saver');
        FileSaver.saveAs("http://localhost:8080/api/vocabulary/Export", "Export.csv");
    }

    render() {
        return (
            <main>
                <h2 style={{ marginBottom: "3%", marginTop: "10%" }}>Import vocabulary</h2>
                <Row style={{ marginBottom: "2%" }}>
                    <Col>
                    <form onSubmit={this.onFormSubmit}>
        <input type="file" onChange={this.onChange} />
        <button type="submit">Upload</button>
      </form>
                    </Col>
                </Row>

                <h2 style={{ marginBottom: "3%", marginTop: "3%" }}>Export vocabulary</h2>
                <Row style={{ marginBottom: "2%" }}>
                    <Col>
                        <React.Fragment>
                        <Button style={{ width: "200px" }} type="button" onClick={this.exportVocab}
                                >Export to Device</Button>
                        </React.Fragment>
                    </Col>
                </Row>
            </main>
        );
    }
}

export default Export;