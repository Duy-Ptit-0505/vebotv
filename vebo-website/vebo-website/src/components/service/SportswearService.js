import axios from "axios";


class SportswearService {
    static BASE_URL = "http://localhost:8080"
    static async getAllSportswearByCustomer(token, key, noPage, categoryName) {
        try {
            const response = await axios.get(`${SportswearService.BASE_URL}/api/web/search?key=${key}&&noPage=${noPage}&&categoryName=${categoryName}`, {
                headers: { Authorization: `Bearer ${token}` }
            })
            return response.data;
        } catch (err) {
            throw err;
        }
    }
    static async getCart(token) {
        try {
            const response = await axios.get(`${SportswearService.BASE_URL}/api/customer/cart`, {
                headers: { Authorization: `Bearer ${token}` }
            })
            return response.data;
        } catch (err) {
            throw err;
        }
    }
    static async getSportswearByID(token, sportswearID) {
        try {
            const response = await axios.get(`${SportswearService.BASE_URL}/api/web/detail/${sportswearID}`, {
                headers: { Authorization: `Bearer ${token}` }
            })
            return response.data;
        } catch (err) {
            throw err;
        }
    }
    static async getUserReviewBySportswearID(token, sportswearID, noPage) {
        try {
            const response = await axios.post(`${SportswearService.BASE_URL}/api/web/user-review`, { sportswearID, noPage }, {
                headers: { Authorization: `Bearer ${token}` }
            })
            return response.data;
        } catch (err) {
            throw err;
        }
    }
    static async addSportswear(token, sportswear) {
        try {
            const response = await axios.post(`${SportswearService.BASE_URL}/api/admin/add-product`, sportswear, {
                headers: { Authorization: `Bearer ${token}` }
            })
            return response.data;
        } catch (err) {
            throw err;
        }
    }
    static async deleteSportswearByID(token, sportswearID) {
        try {
            const response = await axios.get(`${SportswearService.BASE_URL}/api/admin/delete-product/${sportswearID}`, {
                headers: { Authorization: `Bearer ${token}` }
            })
            return response.data;
        } catch (err) {
            throw err;
        }
    }

    static async getAllSportswearByAdmin(token, key, noPage, categoryName) {
        try {
            const response = await axios.get(`${SportswearService.BASE_URL}/api/admin/search?key=${key}&&noPage=${noPage}&&categoryName=${categoryName}`, {
                headers: { Authorization: `Bearer ${token}` }
            })
            return response.data;
        } catch (err) {
            throw err;
        }
    }

    static async getListSportswearRevenueResponseInAMonth(token, sportswearID) {
        try {
            const response = await axios.get(`${SportswearService.BASE_URL}/api/admin/test/${sportswearID}`, {
                headers: { Authorization: `Bearer ${token}` }
            })
            return response.data;
        } catch (err) {
            throw err;
        }
    }
    getListLabelAndValue

    static async getListLabelAndValue(token, month) {
        try {
            const response = await axios.get(`${SportswearService.BASE_URL}/api/admin/chart-admin/${month}`, {
                headers: { Authorization: `Bearer ${token}` }
            })
            return response.data;
        } catch (err) {
            throw err;
        }
    }
    static async getAllListSportswearRevenueResponseInAMonth(token) {
        try {
            const response = await axios.get(`${SportswearService.BASE_URL}/api/admin/chart`, {
                headers: { Authorization: `Bearer ${token}` }
            })
            return response.data;
        } catch (err) {
            throw err;
        }
    }
    static async createOrderItem(token, orderItemRequest) {
        try {
            const response = await axios.post(`${SportswearService.BASE_URL}/api/customer/add-to-cart`, orderItemRequest, {
                headers: { Authorization: `Bearer ${token}` }
            })
            return response.data;
        } catch (err) {
            throw err;
        }
    }
}
export default SportswearService;