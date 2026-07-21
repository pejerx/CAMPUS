import {
  IconArrowLeft,
} from "@tabler/icons-react";

import {
  ChangeEvent,
  FormEvent,
  useEffect,
  useState,
} from "react";

import {
  useLocation,
  useNavigate,
} from "react-router-dom";

import "../css/style.css";
import "../css/component_style.css";

import ClaimForm from "./component_claim_form";
import { submitClaimRequest } from "./claim_request_api";

type ItemReport = {
  id: number;
  userId?: string;
  itemName?: string;
  reportType?: string;
  itemType?: string;
  category?: string;
  location?: string;
  lastSeenLocation?: string;
  description?: string;
  status?: string;
  imagePath?: string;
  imageUrl?: string;
  createdAt?: string;
};

function ClaimRequestPage() {

  const navigate = useNavigate();
  const location = useLocation();

  const item =
    location.state?.item as ItemReport;

    const user = JSON.parse(
        localStorage.getItem("user") || "{}"
        );

  const [
    itemDescription,
    setItemDescription,
  ] = useState("");

  const [
    additionalInformation,
    setAdditionalInformation,
  ] = useState("");

  const [
    proofImage,
    setProofImage,
  ] = useState<File | null>(null);

  const [
    previewImage,
    setPreviewImage,
  ] = useState("");

  const [loading, setLoading] =
    useState(false);

  useEffect(() => {

    if (!item) {

      navigate("/explore", {
        replace: true,
      });

    }

  }, [item, navigate]);

  const handleImageChange = (
    event: ChangeEvent<HTMLInputElement>
  ) => {

    const file =
      event.target.files?.[0];

    if (!file) return;

    setProofImage(file);

    setPreviewImage(
      URL.createObjectURL(file)
    );

  };

  const handleSubmit = async (
    event: FormEvent
  ) => {

    event.preventDefault();

    if (!item) return;

    if (!itemDescription) {

      alert(
        "Please complete all required fields."
      );

      return;

    }

    try {

      setLoading(true);

      const user =
        JSON.parse(
          localStorage.getItem("user") || "{}"
        );

      const formData = new FormData();

      formData.append(
        "itemReportId",
        String(item.id)
      );

      formData.append(
        "claimantId",
        user.studentId || user.id || ""
      );

      formData.append(
        "claimantName",
        `${user.firstName} ${user.lastName}`
        );

        formData.append(
        "claimantEmail",
        user.email
        );

        formData.append(
        "claimantPhone",
        user.contactNumber
        );

      formData.append(
        "itemDescription",
        itemDescription
      );

      formData.append(
        "additionalInformation",
        additionalInformation
      );

      if (proofImage) {

        formData.append(
          "proofImage",
          proofImage
        );

      }

      await submitClaimRequest(
        formData
      );

      navigate(
        "/claim-success"
      );

    } catch (error) {

      console.error(error);

      alert(
        "Failed to submit claim request."
      );

    } finally {

      setLoading(false);

    }

  };

  const imageSource =
    item?.imageUrl
      ? item.imageUrl
      : item?.imagePath
      ? item.imagePath.startsWith("http")
        ? item.imagePath
        : `http://localhost:8080/${item.imagePath}`
      : "";

  return (

    <div className="claim-page">

      <div className="claim-container">

        <button
          className="claim-back-btn"
          onClick={() => navigate(-1)}
        >
          <IconArrowLeft size={18} />
          Back
        </button>

        <h1>
          Claim Item
        </h1>

        <p className="claim-subtitle">
          Fill in the information below to
          request ownership of this item.
        </p>

        <div className="claim-layout">

          <div className="claim-item-preview">

            {imageSource ? (

              <img
                src={imageSource}
                alt={item?.itemName}
              />

            ) : (

              <div className="claim-placeholder">

                📦

              </div>

            )}

            <h2>
              {item?.itemName}
            </h2>

            <span
              className={
                item?.reportType === "Lost"
                  ? "lf-badge lost"
                  : "lf-badge found"
              }
            >
              {item?.reportType}
            </span>

            <p>
              <strong>Category</strong>
              <br />
              {item?.category}
            </p>

            <p>
              <strong>Location</strong>
              <br />
              {item?.location ||
                item?.lastSeenLocation}
            </p>

          </div>

          <ClaimForm
            firstName={user.firstName}
            lastName={user.lastName}
            email={user.email}
            contactNumber={user.contactNumber}
            itemDescription={itemDescription}
            additionalInformation={additionalInformation}
            previewImage={previewImage}
            loading={loading}
            onDescriptionChange={setItemDescription}
            onAdditionalInformationChange={setAdditionalInformation}
            onImageChange={handleImageChange}
            onCancel={() => navigate(-1)}
            onSubmit={handleSubmit}
            />
        </div>

      </div>

    </div>

  );

}

export default ClaimRequestPage;